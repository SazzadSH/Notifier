package com.demo.notificationproducer.services.impl;

import com.demo.notificationproducer.models.dtos.NotificationDTO;
import com.demo.notificationproducer.models.dtos.NotificationTargetDTO;
import com.demo.notificationproducer.models.entities.Location;
import com.demo.notificationproducer.models.entities.Notification;
import com.demo.notificationproducer.models.entities.NotificationTarget;
import com.demo.notificationproducer.models.enums.NotificationStatus;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.repositories.*;
import com.demo.notificationproducer.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.relational.core.sql.In;
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
	private LocationRepositoty locationRepositoty;
	private EducationalInstituteRepository educationalInstituteRepository;
	private UserRepository userRepository;
	private OfficeRepository officeRepository;
	private DesignationRepository designationRepository;

	@Autowired
	public void setNotificationRepository(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Autowired
	public void setLocationRepositoty(LocationRepositoty locationRepositoty) {
		this.locationRepositoty = locationRepositoty;
	}

	@Autowired
	public void setEducationalInstituteRepository(EducationalInstituteRepository educationalInstituteRepository) {
		this.educationalInstituteRepository = educationalInstituteRepository;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setOfficeRepository(OfficeRepository officeRepository) {
		this.officeRepository = officeRepository;
	}

	@Autowired
	public void setDesignationRepository(DesignationRepository designationRepository) {
		this.designationRepository = designationRepository;
	}

	/**
	 * @param notificationTypeEnumSet indicates the type of notifications: Regular, Notice, Bulk
	 * @param content message to notify
	 * @param notificationTargetDTOSet indicates whom to notify
	 * @param createdBy username of who created the notification
	 * @param notificationFrom username / name of whom the notification is on behalf
	 * @param scheduledAt scheduled time to notify
	 * @param expireAt notification expiration time
	 */
	@Override
	public ResponseEntity<Object> createNotification(Integer createdBy, EnumSet<NotificationType> notificationTypeEnumSet, String content,
	                                                 Set<NotificationTargetDTO> notificationTargetDTOSet, String notificationFrom,
	                                                 LocalDateTime scheduledAt, LocalDateTime expireAt) {
		if(notificationTypeEnumSet != null && content != null
				&& notificationTargetDTOSet != null && !notificationTargetDTOSet.isEmpty()) {
			NotificationDTO notificationDTO = NotificationDTO.builder()
					.content(content)
					.notificationTargetDTOSet(notificationTargetDTOSet)
					.createdBy(createdBy)
					.notificationFrom(notificationFrom)
					.scheduledAt(scheduledAt)
					.expireAt(expireAt)
					.build();
			return this.createNotification(notificationDTO);
		} else {
			return null;
		}
	}

	/**
	 * @param notificationTypeEnumSet indicates the type of notifications: Regular, Notice, Bulk
	 * @param content message to notify
	 * @param notificationTargetDTOSet indicates whom to notify
	 */
	@Override
	public ResponseEntity<Object> createNotification(Integer createdBy, EnumSet<NotificationType> notificationTypeEnumSet, String content,
	                                                 Set<NotificationTargetDTO> notificationTargetDTOSet) {
		return this.createNotification(createdBy, notificationTypeEnumSet, content, notificationTargetDTOSet, null,
				null, null);
	}

	/**
	 * @param notificationDTO is object containing the parameter and attributes of one single notification
	 * @return HTTP Response
	 */
	@Override
	public ResponseEntity<Object> createNotification(NotificationDTO notificationDTO) {
		log.info("SHLOG:: Notification: " + notificationDTO);
		if(!this.validateNotification(notificationDTO)) {
			log.info("SHLOG:: NotificationDTO isn't valid");
			return ResponseEntity.badRequest().build();
		}
		//Notification expiration
		if(notificationDTO.getExpireAt() == null) notificationDTO.setExpireAt(this.getDefaultNotificationExpiryTime());

		Notification notification = this.buildNotificationEntity(notificationDTO);
		log.info("SHLOG:: Built notification entity: " + notification);
		log.info("SHLOG:: Built notification criteria: " + notification.getNotificationTargets());
		notificationRepository.save(notification);
		log.info("SHLOG:: Notification created");
		return ResponseEntity.ok().build();
	}

	private LocalDateTime getDefaultNotificationExpiryTime() {
		String expirationInDays = environment.getProperty("notification.default.expiry");
		if(expirationInDays != null) return LocalDateTime.now().plusDays(Integer.parseInt(expirationInDays));
		else return LocalDateTime.now().plusDays(3);
	}

	// validates notification parameters
	private Boolean validateNotification(final NotificationDTO notificationDTO) {
		log.info("SHLOG:: Validating Notification");
		if (notificationDTO.getNotificationTypeEnumSet() == null || notificationDTO.getNotificationTypeEnumSet().isEmpty()) return false;
		if(notificationDTO.getCreatedBy() == null) return false;

		//If expire time exists, expire time must be later than scheduled time if it exists.
		//Otherwise, it must be later than notification created time
		if(notificationDTO.getScheduledAt() != null) {
			if(!notificationDTO.getScheduledAt().isAfter(LocalDateTime.now())) return false;
			if(notificationDTO.getExpireAt() != null &&
					!notificationDTO.getExpireAt().isAfter(notificationDTO.getScheduledAt())) return false;
		} else {
			if(notificationDTO.getExpireAt() != null &&
					!notificationDTO.getExpireAt().isAfter(LocalDateTime.now())) return false;
		}

		//Notification content must exist and Notify Criteria must be valid
		return (notificationDTO.getContent() == null || !notificationDTO.getContent().trim().isEmpty()) &&
				this.validateTarget(notificationDTO.getNotificationTargetDTOSet());
	}

	// validates notification criteria
	private Boolean validateTarget(Set<NotificationTargetDTO> notificationTargetDTOSet) {
		log.info("SHLOG:: Validating Notify targets");
		if (notificationTargetDTOSet == null || notificationTargetDTOSet.isEmpty()) return false;
		return notificationTargetDTOSet.parallelStream()
						.allMatch(this::isValidTarget);
	}

	private Boolean isValidTarget(NotificationTargetDTO notificationTargetDTO) {
		//Either institutes or office or both must be selected unless pds id is present
		if(notificationTargetDTO.getUser() == null &&
				((notificationTargetDTO.getIncludeInstitutes() == null ||
						notificationTargetDTO.getIncludeInstitutes().equals(Boolean.FALSE)) &&
						(notificationTargetDTO.getIncludeOffices() == null ||
								notificationTargetDTO.getIncludeOffices().equals(Boolean.FALSE)))) return false;

		//Without any of these, target must be invalid
		return notificationTargetDTO.getLocation() != null || notificationTargetDTO.getInstitute() != null ||
				notificationTargetDTO.getOffice() != null || notificationTargetDTO.getUser() != null ||
				(notificationTargetDTO.getBulkFile() != null && !notificationTargetDTO.getBulkFile().trim().isEmpty());
	}

	// creates notification entity to persist
	private Notification buildNotificationEntity(NotificationDTO notificationDTO) {
		log.info("SHLOG:: Building Notification Entity");
		return Notification.builder()
				.createdBy(userRepository.getReferenceById(notificationDTO.getCreatedBy()))
				.notificationTypes(this.stringifyEnumList(notificationDTO.getNotificationTypeEnumSet()))
				.notificationTargets(this.buildTargetEntitySet(notificationDTO.getNotificationTargetDTOSet()))
				.notificationFrom(notificationDTO.getNotificationFrom())
				.content(notificationDTO.getContent())
				.createdAt(LocalDateTime.now())
				.scheduledAt(notificationDTO.getScheduledAt())
				.expiresAt(notificationDTO.getExpireAt())
				.status(notificationDTO.getScheduledAt() == null ? NotificationStatus.QUEUED : NotificationStatus.SCHEDULED)
				.build();
	}

	private Set<NotificationTarget> buildTargetEntitySet(Set<NotificationTargetDTO> notificationTargetDTOSet) {
		log.info("Creating target entity set");
		Set<NotificationTarget> notificationTargetSet = notificationTargetDTOSet.stream()
				.map(this::convertTargetDTOtoEntity)
				.collect(Collectors.toSet());
		log.info("Notification taget entity set: " + notificationTargetSet);
		return notificationTargetSet;
	}

	private NotificationTarget convertTargetDTOtoEntity(NotificationTargetDTO notificationTargetDTO) {
		if(notificationTargetDTO.getBulkFile() != null && notificationTargetDTO.getBulkFile().trim().isEmpty())
			return NotificationTarget.builder()
					.bulkFile(notificationTargetDTO.getBulkFile())
					.build();
		else if(notificationTargetDTO.getUser() != null) return NotificationTarget.builder()
				.user(userRepository.getReferenceById(notificationTargetDTO.getUser()))
				.build();

		NotificationTarget notificationTarget = NotificationTarget.builder()
				.includeOffice(notificationTargetDTO.getIncludeOffices())
				.includeInstitutes(notificationTargetDTO.getIncludeInstitutes())
				.userType(notificationTargetDTO.getUserType())
				.build();

		if(notificationTargetDTO.getDesignation() != null)
			notificationTarget.setDesignation(designationRepository.getReferenceById(notificationTargetDTO.getDesignation()));

		if(notificationTargetDTO.getInstitute() != null && notificationTargetDTO.getIncludeInstitutes().equals(Boolean.TRUE))
			notificationTarget.setEducationalInstitute(educationalInstituteRepository.getReferenceById(notificationTargetDTO.getInstitute()));

		if(notificationTargetDTO.getOffice() != null && notificationTargetDTO.getIncludeOffices().equals(Boolean.TRUE))
			notificationTarget.setOffice(officeRepository.getReferenceById(notificationTargetDTO.getOffice()));

		if(notificationTargetDTO.getInstitute() != null || notificationTargetDTO.getOffice() != null) return notificationTarget;

		notificationTarget.setLocation(locationRepositoty.getReferenceById(notificationTargetDTO.getLocation()));
		return notificationTarget;
	}

	private String stringifyEnumList(EnumSet<NotificationType> notificationTypeEnumSet) {
		return notificationTypeEnumSet.stream()
				.map(Enum::name)
				.collect(Collectors.joining(", "));
	}
}
