package com.demo.notificationproducer.services;

import com.demo.notificationproducer.models.dtos.NotificationDTO;

import com.demo.notificationproducer.models.dtos.NotificationTargetDTO;
import com.demo.notificationproducer.models.enums.NotificationType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.EnumSet;

import java.util.Set;

public interface NotificationService {
	ResponseEntity<Object> createNotification(EnumSet<NotificationType> notificationTypeEnumSet, NotificationDTO notificationDTO);
	ResponseEntity<Object> createNotification(EnumSet<NotificationType> notificationTypeEnumSet, String content,
	                                          Set<NotificationTargetDTO> notificationTargetDTO, String createdBy, String notificationFrom,
	                                          LocalDateTime scheduledAt, LocalDateTime expireAt, Boolean email);
	ResponseEntity<Object> createNotification(EnumSet<NotificationType> notificationTypeEnumSet, String content, Set<NotificationTargetDTO> notificationTargetDTOSet);



}
