package com.demo.notificationproducer.services;

import com.demo.notificationproducer.models.dtos.NotificationDTO;

import com.demo.notificationproducer.models.dtos.NotificationTargetDTO;
import com.demo.notificationproducer.models.enums.NotificationType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface NotificationService {
	ResponseEntity<Object> createNotification(Set<NotificationType> notificationTypeSet, NotificationDTO notificationDTO);
	ResponseEntity<Object> createNotification(NotificationType notificationType, String content,
	                                          Set<NotificationTargetDTO> notificationTargetDTO, String createdBy, String notificationFrom,
	                                          LocalDateTime scheduledAt, LocalDateTime expireAt, Boolean email);
	ResponseEntity<Object> createNotification(NotificationType notificationType, String content, Set<NotificationTargetDTO> notificationTargetDTOSet);



}
