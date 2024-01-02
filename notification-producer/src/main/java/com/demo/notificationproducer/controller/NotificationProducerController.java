package com.demo.notificationproducer.controller;

import com.demo.notificationproducer.models.dtos.NotificationDTO;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.services.NotificationProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
			@PathVariable("notificationType") final NotificationType notificationType,
			@RequestBody final NotificationDTO notificationDTO) {
		log.info("SHLOG:: Notifciation DTO: " + notificationDTO);
		return notificationProducerService.createNotification(notificationType, notificationDTO);
	}
}
