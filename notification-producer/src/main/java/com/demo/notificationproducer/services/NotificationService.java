package com.demo.notificationproducer.services;

import com.demo.notificationproducer.models.dtos.NotificationDTO;

import com.demo.notificationproducer.models.dtos.NotificationTargetDTO;
import com.demo.notificationproducer.models.enums.NotificationType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.EnumSet;

import java.util.Set;

public interface NotificationService {
	ResponseEntity<Object> createNotification(NotificationDTO notificationDTO);
	ResponseEntity<Object> createNotification(Integer createdBy, EnumSet<NotificationType> notificationTypeEnumSet, String content,
	                                          Set<NotificationTargetDTO> notificationTargetDTOSet, String notificationFrom,
	                                          LocalDateTime scheduledAt, LocalDateTime expireAt);
	ResponseEntity<Object> createNotification(Integer createdBy, EnumSet<NotificationType> notificationTypeEnumSet, String content,
	                                          Set<NotificationTargetDTO> notificationTargetDTOSet);



}
