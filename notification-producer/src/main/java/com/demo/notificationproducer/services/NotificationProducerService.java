package com.demo.notificationproducer.services;

import com.demo.notificationproducer.models.dtos.NotificationDTO;

import com.demo.notificationproducer.models.dtos.NotifyCriteriaDTO;
import com.demo.notificationproducer.models.entities.Notification;
import com.demo.notificationproducer.models.enums.NotificationType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Set;

public interface NotificationProducerService {
	ResponseEntity<Object> createNotification(NotificationType notificationType, NotificationDTO notificationDTO);
	ResponseEntity<Object> createNotification(NotificationType notificationType, String content,
	                        Set<NotifyCriteriaDTO> notifyCriteriaDTO, String createdBy, String notificationFrom,
	                        LocalDateTime scheduledAt, LocalDateTime expireAt, Boolean email);
	ResponseEntity<Object> createNotification(NotificationType notificationType, String content, Set<NotifyCriteriaDTO> notifyCriteriaDTOSet);



}
