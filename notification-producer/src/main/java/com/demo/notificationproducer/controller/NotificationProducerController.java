package com.demo.notificationproducer.controller;

import com.demo.notificationproducer.models.dtos.NotificationDTO;
import com.demo.notificationproducer.models.dtos.NotifyCriteriaDTO;
import com.demo.notificationproducer.models.entities.NotifyCriteria;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.services.NotificationProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

	@PostMapping("/notify2/{notificationType}")
	private ResponseEntity createNotification2(
			@PathVariable("notificationType") final NotificationType notificationType,
			@RequestParam final String content,
			@RequestParam final String pdsId) {
		return notificationProducerService.createNotification(notificationType, content,
				Set.of(NotifyCriteriaDTO.builder().pdsId(pdsId).build()));
	}
}
