package com.demo.notificationproducer.controller;

import com.demo.notificationproducer.models.dtos.NotificationDTO;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.services.NotificationProducerService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/producer")
public class NotificationProducerController {
	private NotificationProducerService notificationProducerService;

	@Autowired
	public void setNotificationProducerService(NotificationProducerService notificationProducerService) {
		this.notificationProducerService = notificationProducerService;
	}

	@PostMapping("/notify/{notificationType}")
	private ResponseEntity createNotification(
			@PathVariable("notificationType") NotificationType notificationType,
			@RequestBody NotificationDTO notificationDTO) {

		return null;
	}
}
