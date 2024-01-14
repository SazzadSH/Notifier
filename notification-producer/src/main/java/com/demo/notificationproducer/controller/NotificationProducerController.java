package com.demo.notificationproducer.controller;

import com.demo.notificationproducer.models.dtos.NotificationDTO;
import com.demo.notificationproducer.models.dtos.NotificationTargetDTO;
import com.demo.notificationproducer.models.entities.Notification;
import com.demo.notificationproducer.models.entities.NotificationTarget;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.repositories.NotificationRepository;
import com.demo.notificationproducer.services.NotificationService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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
	private NotificationRepository notificationRepository;

	@Autowired
	public void setNotificationProducerService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Autowired
	public void setNotificationRepository(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@PostMapping("/notify/{notificationType}")
	private ResponseEntity createNotification(
			@PathVariable("notificationType") final EnumSet<NotificationType> notificationTypeEnumSet,
			@RequestBody final NotificationDTO notificationDTO) {
		log.info("SHLOG:: Notifciation DTO: " + notificationDTO);
		notificationDTO.setNotificationTypeEnumSet(notificationTypeEnumSet);
		return notificationService.createNotification(notificationDTO);
	}

	@PostMapping("/notify2/")
	private ResponseEntity createNotification2(
//			@RequestParam final Integer createdBy,
//			@RequestParam final EnumSet<NotificationType> notificationTypeEnumSet,
//			@RequestParam final String content,
//			@RequestParam final Set<NotificationTargetDTO> notificationTargetDTOSet
			) {
		log.info("Triggering crate notification");
		notificationService.createNotification(NotificationDTO.builder()
				.createdBy(24)
				.notificationTypeEnumSet(EnumSet.of(NotificationType.PUSH, NotificationType.EMAIL, NotificationType.PERSISTENT))
				.content("প্রথম নোটিফিকেশন! প্রেরন করা হচ্ছে!")
				.notificationTargetDTOSet(Set.of(
						NotificationTargetDTO.builder()
								.location(16)
								.includeInstitutes(true)
								.build(),
						NotificationTargetDTO.builder()
								.location(35)
								.includeOffices(true)
								.build()
				))
				.build()
		);
//		Set<NotificationTargetDTO> notificationTargetDTOSet = Set.of(
//				NotificationTargetDTO.builder()
//						.location(16)
//						.includeInstitutes(true)
//						.build(),
//				NotificationTargetDTO.builder()
//						.location(35)
//						.includeOffices(true)
//						.build()
//		);
		return null;
//		return notificationService.createNotification(createdBy, notificationTypeEnumSet, content, notificationTargetDTOSet);
	}

	@GetMapping("/getNotifications")
	private ResponseEntity getNotification() {
		Notification notification = notificationRepository.findById(252).get();



		return ResponseEntity.ok().body(notification.getContent());
	}
}
