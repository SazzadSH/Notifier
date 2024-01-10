package com.demo.notificationproducer.controller;

import com.demo.notificationproducer.models.dtos.NotificationDTO;
import com.demo.notificationproducer.models.dtos.NotificationTargetDTO;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/v1/producer")
public class NotificationProducerController {
	private NotificationService notificationService;

	@Autowired
	public void setNotificationProducerService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@PostMapping("/notify/{notificationType}")
	private ResponseEntity createNotification(
			@PathVariable("notificationType") final EnumSet<NotificationType> notificationTypeEnumSet,
			@RequestBody final NotificationDTO notificationDTO) {
		log.info("SHLOG:: Notifciation DTO: " + notificationDTO);
		return notificationService.createNotification(notificationTypeEnumSet, notificationDTO);
	}

	@PostMapping("/notify2/{notificationType}")
	private ResponseEntity createNotification2(
			@PathVariable("notificationType") final EnumSet<NotificationType> notificationTypeEnumSet,
			@RequestParam final String content,
			@RequestParam final Integer userId) {
		return notificationService.createNotification(notificationTypeEnumSet, content,
				Set.of(NotificationTargetDTO.builder().user(userId).build()));
	}
}
