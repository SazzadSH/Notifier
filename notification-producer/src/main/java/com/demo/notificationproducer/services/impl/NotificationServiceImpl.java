package com.demo.notificationproducer.services.impl;

import com.demo.notificationproducer.models.dtos.NotificationDTO;
import com.demo.notificationproducer.models.dtos.NotificationTargetDTO;
import com.demo.notificationproducer.models.entities.Notification;
import com.demo.notificationproducer.models.enums.NotificationStatus;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.repositories.NotificationRepository;
import com.demo.notificationproducer.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private Environment environment;
	private NotificationRepository notificationRepository;

	@Autowired
	public void setNotificationRepository(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	/**
	 * @param notificationType indicates the type of notifications: Regular, Notice, Bulk
	 * @param content message to notify
	 * @param notificationTargetDTOSet indicates whom to notify
	 * @param createdBy username of who created the notification
	 * @param notificationFrom username / name of whom the notification is on behalf
	 * @param scheduledAt scheduled time to notify
	 * @param expireAt notification expiration time
	 * @param email flag indicating the notification should be sent to email or not
	 */
	@Override
	public ResponseEntity<Object> createNotification(NotificationType notificationType, String content, Set<NotificationTargetDTO> notificationTargetDTOSet, String createdBy, String notificationFrom, LocalDateTime scheduledAt, LocalDateTime expireAt, Boolean email) {
		if(notificationType != null && content != null
				&& notificationTargetDTOSet != null && !notificationTargetDTOSet.isEmpty()) {
			NotificationDTO notificationDTO = NotificationDTO.builder()
					.content(content)
					.notificationTargetDTOSet(notificationTargetDTOSet)
					.createdBy(createdBy)
					.notificationFrom(notificationFrom)
					.scheduledAt(scheduledAt)
					.expireAt(expireAt)
					.build();
			return this.createNotification(notificationType, notificationDTO);
		} else {
			return null;
		}
	}

	/**
	 * @param notificationType indicates the type of notifications: Regular, Notice, Bulk
	 * @param content message to notify
	 * @param notificationTargetDTOSet indicates whom to notify
	 */
	@Override
	public ResponseEntity<Object> createNotification(NotificationType notificationType, String content, Set<NotificationTargetDTO> notificationTargetDTOSet) {
		return this.createNotification(notificationType, content, notificationTargetDTOSet, null,
				null, null, null, null);
	}

	/**
	 * @param notificationType indicates the type of notifications: Regular, Notice, Bulk
	 * @param notificationDTO is object containing the parameter and attributes of one single notification
	 * @return HTTP Response
	 */
	@Override
	public ResponseEntity<Object> createNotification(EnumSet<NotificationType> notificationTypeEnumSet, NotificationDTO notificationDTO) {
		notificationDTO.setCreatedAt(LocalDateTime.now());
		log.info("SHLOG:: Notification: " + notificationDTO);
		if(!this.validateNotification(notificationTypeEnumSet, notificationDTO)) {
			log.info("SHLOG:: NotificationDTO isn't valid");
			return ResponseEntity.badRequest().build();
		}

		//Notification expiration
		if(notificationDTO.getExpireAt() == null) {
			String expirationInDays = environment.getProperty("notification.default.expiry");
			if(expirationInDays != null) notificationDTO.setExpireAt(LocalDateTime.now().plusDays(Integer.parseInt(expirationInDays)));
			else notificationDTO.setExpireAt(LocalDateTime.now().plusDays(3));
		}

		Notification notification = this.buildNotificationEntity(notificationTypeEnumSet, notificationDTO);
		log.info("SHLOG:: Built notification entity: " + notification);
		log.info("SHLOG:: Built notification criteria: " + notification.getNotifyCriteria());
		notificationRepository.save(notification);
		log.info("SHLOG:: Notification created");
		return ResponseEntity.ok().build();
	}

	// validates notification parameters
	private Boolean validateNotification(final Set<NotificationType> notificationTypeSet, final NotificationDTO notificationDTO) {
		log.info("SHLOG:: Validating Notification");
		if (notificationTypeSet == null || notificationTypeSet.isEmpty()) return false;

		//If expire time exists, expire time must be later than scheduled time if it exists.
		//Otherwise, it must be later than notification created time
		if(notificationDTO.getScheduledAt() != null) {
			if(!notificationDTO.getScheduledAt().isAfter(notificationDTO.getCreatedAt())) return false;
			if(notificationDTO.getExpireAt() != null &&
					!notificationDTO.getExpireAt().isAfter(notificationDTO.getScheduledAt())) return false;
		} else {
			if(notificationDTO.getExpireAt() != null &&
					notificationDTO.getExpireAt().isAfter(notificationDTO.getCreatedAt())) return false;
		}

		//Notification content must exist and Notify Criteria must be valid
		return (notificationDTO.getContent() == null || !notificationDTO.getContent().trim().isEmpty()) &&
				this.validateCriteria(notificationDTO.getNotificationTargetDTOSet());
	}

	// validates notification criteria
	private Boolean validateCriteria(Set<NotificationTargetDTO> notificationTargetDTOSet) {
		log.info("SHLOG:: Validating Notify Criteria");
		if (notificationTargetDTOSet == null || notificationTargetDTOSet.isEmpty()) return false;
		for(NotificationTargetDTO notificationTargetDTO : notificationTargetDTOSet) {
			//Either institutes or office or both must be selected unless pds id is present
			if(notificationTargetDTO.getUser() == null &&
					((notificationTargetDTO.getIncludeInstitutes() == null ||
							notificationTargetDTO.getIncludeInstitutes().equals(Boolean.FALSE)) &&
							(notificationTargetDTO.getIncludeOffices() == null ||
									notificationTargetDTO.getIncludeOffices().equals(Boolean.FALSE)))) return false;

			//Without any of these, criteria must be invalid
			if(notificationTargetDTO.getLocation() == null && notificationTargetDTO.getInstitute() == null &&
					notificationTargetDTO.getOffice() == null && notificationTargetDTO.getUser() == null) return false;
		}
		log.info("Notify criteria is valid");
		return true;
	}

	// creates notification entity to persist
	private Notification buildNotificationEntity(NotificationType notificationType, NotificationDTO notificationDTO) {
		log.info("SHLOG:: Building Notification Entity");
		return Notification.builder()
				.notificationTypes(notificationType)
				.notifyCriteria(notificationDTO.getNotificationTargetDTOSet().stream()
						.map(NotificationServiceImpl::convertCriteriaDTOtoEntity)
						.collect(Collectors.toSet()))
				.notificationFrom(notificationDTO.getNotificationFrom())
				.content(notificationDTO.getContent())
				.createdAt(notificationDTO.getCreatedAt())
				.scheduledAt(notificationDTO.getScheduledAt())
				.expiresAt(notificationDTO.getExpireAt())
				.status(NotificationStatus.QUEUED)
				.email(notificationDTO.getEmail() == Boolean.TRUE ? Boolean.TRUE : Boolean.FALSE)
				.build();
	}

	private static com.demo.notificationproducer.models.entities.NotificationTarget convertCriteriaDTOtoEntity(NotificationTargetDTO notificationTargetDTO) {
			return com.demo.notificationproducer.models.entities.NotificationTarget.builder().location(notificationTargetDTO.getLocation())
					.userType(notificationTargetDTO.getUserTypes())
					.institute(notificationTargetDTO.getInstitute())
					.office(notificationTargetDTO.getOffice())
					.includeOffice(notificationTargetDTO.getIncludeOffices())
					.includeInstitutes(notificationTargetDTO.getIncludeInstitutes())
					.pdsId(notificationTargetDTO.getPdsId())
					.build();
	}

	private String stringifyEnumList(EnumSet<NotificationType> notificationTypeEnumSet) {
		notificationTypeEnumSet.stream()
				.map(Enum::name)
				.collect(Collectors.joining(", "));
	}
}
