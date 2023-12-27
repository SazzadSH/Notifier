package com.demo.notificationproducer.services;

import com.demo.notificationproducer.models.dtos.NotificationDTO;

import com.demo.notificationproducer.models.enums.NotificationType;
import org.springframework.http.ResponseEntity;

public interface NotificationProducerService {
	ResponseEntity<Object> createNotification(NotificationType notificationType, NotificationDTO notificationDTO);
}
