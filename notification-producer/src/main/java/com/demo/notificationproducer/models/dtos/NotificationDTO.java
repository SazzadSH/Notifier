package com.demo.notificationproducer.models.dtos;

import com.demo.notificationproducer.models.entities.Notification;
import com.demo.notificationproducer.models.enums.NotificationType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NotificationDTO {
	private String createdBy;
	@PastOrPresent
	private LocalDateTime createdAt;
	@FutureOrPresent
	private LocalDateTime scheduledAt;
	@Future
	private LocalDateTime expireAt;
	private String notificationFrom;
	@NotNull
	private NotificationType notificationType;
	private Boolean email;
	@NotNull
	@NotEmpty
	@NotBlank
	private String content;
}
