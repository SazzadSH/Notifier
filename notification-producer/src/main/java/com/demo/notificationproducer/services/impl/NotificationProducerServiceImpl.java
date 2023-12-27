package com.demo.notificationproducer.services.impl;

import com.demo.notificationproducer.models.dtos.NotificationDTO;
import com.demo.notificationproducer.models.dtos.NotifyCriteriaDTO;
import com.demo.notificationproducer.models.entities.Notification;
import com.demo.notificationproducer.models.entities.NotifyCriteria;
import com.demo.notificationproducer.models.enums.NotificationStatus;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.repositories.NotificationRepository;
import com.demo.notificationproducer.services.NotificationProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationProducerServiceImpl implements NotificationProducerService {

	@Autowired
	private Environment environment;
	private NotificationRepository notificationRepository;

	@Autowired
	public void setNotificationRepository(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public ResponseEntity<Object> createNotification(NotificationType notificationType, NotificationDTO notificationDTO) {
		notificationDTO.setCreatedAt(LocalDateTime.now());
		if(!this.validateNotification(notificationType, notificationDTO)) {
			return ResponseEntity.badRequest().build();
		}

		//Notification expiration
		if(notificationDTO.getExpireAt() == null) {
			String expirationInDays = environment.getProperty("notification.default.expiry");
			if(expirationInDays != null) notificationDTO.setExpireAt(LocalDateTime.now().plusDays(Integer.parseInt(expirationInDays)));
			else notificationDTO.setExpireAt(LocalDateTime.now().plusDays(3));
		}

		Notification notification = this.buildNotificationEntity(notificationType, notificationDTO);
		notificationRepository.save(notification);

		return ResponseEntity.ok().build();
	}

	// validates notification parameters
	private Boolean validateNotification(NotificationType notificationType, NotificationDTO notificationDTO) {
		if(notificationDTO.getScheduledAt() != null) {
			if(!notificationDTO.getScheduledAt().isAfter(notificationDTO.getCreatedAt())) return false;
			if(notificationDTO.getExpireAt() != null && !notificationDTO.getExpireAt().isAfter(notificationDTO.getScheduledAt())) return false;
		} else {
			if(notificationDTO.getExpireAt().isAfter(notificationDTO.getCreatedAt())) return false;
		}

		return (notificationDTO.getContent() == null || !notificationDTO.getContent().trim().isEmpty()) && this.validateCriteria(notificationDTO.getNotifyCriteria());
	}

	// validates notification criteria
	private Boolean validateCriteria(Set<NotifyCriteriaDTO> notifyCriteriaDTOSet) {
		if (notifyCriteriaDTOSet == null || notifyCriteriaDTOSet.isEmpty()) return false;
		for(NotifyCriteriaDTO notifyCriteriaDTO : notifyCriteriaDTOSet) {
			if(notifyCriteriaDTO.getIncludeInstitutes().equals(Boolean.FALSE) &&
			notifyCriteriaDTO.getIncludeOffices().equals(Boolean.FALSE)) return false;

			if(notifyCriteriaDTO.getUserTypes() == null) return false;

			if((notifyCriteriaDTO.getLocation() == null || notifyCriteriaDTO.getLocation().trim().isEmpty())
			&& (notifyCriteriaDTO.getInstitute() == null || notifyCriteriaDTO.getInstitute().trim().isEmpty())
			&& (notifyCriteriaDTO.getOffice() == null || notifyCriteriaDTO.getOffice().trim().isEmpty())
			&& notifyCriteriaDTO.getPdsId() == null || notifyCriteriaDTO.getPdsId().trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	// creates notification entity to persist
	private Notification buildNotificationEntity(NotificationType notificationType, NotificationDTO notificationDTO) {
		return Notification.builder()
				.notificationType(notificationType)
				.notifyCriteria(notificationDTO.getNotifyCriteria().stream()
						.map(NotificationProducerServiceImpl::convertCriteriaDTOtoEntity)
						.collect(Collectors.toSet()))
				.notificationFrom(notificationDTO.getNotificationFrom())
				.content(notificationDTO.getContent())
				.createdAt(notificationDTO.getCreatedAt())
				.scheduledAt(notificationDTO.getScheduledAt())
				.expireAt(notificationDTO.getExpireAt())
				.status(NotificationStatus.QUEUED)
				.email(notificationDTO.getEmail())
				.build();
	}

	private static NotifyCriteria convertCriteriaDTOtoEntity(NotifyCriteriaDTO notifyCriteriaDTO) {
			return NotifyCriteria.builder().location(notifyCriteriaDTO.getLocation())
					.userType(notifyCriteriaDTO.getUserTypes())
					.institute(notifyCriteriaDTO.getInstitute())
					.office(notifyCriteriaDTO.getOffice())
					.includeOffice(notifyCriteriaDTO.getIncludeOffices())
					.includeInstitutes(notifyCriteriaDTO.getIncludeInstitutes())
					.pdsId(notifyCriteriaDTO.getPdsId())
					.build();
	}
}
