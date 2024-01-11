package com.demo.notificationproducer.models.dtos;

import com.demo.notificationproducer.models.entities.NotificationTarget;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class NotificationDTO {
	private Integer createdBy;
	private LocalDateTime scheduledAt;
	private LocalDateTime expireAt;
	private String notificationFrom;
	@NotBlank(message = "Notification content is empty")
	private String content;
	@NotNull(message = "Notify Criteria is null.")
	private Set<NotificationTargetDTO> notificationTargetDTOSet;
	@NotNull
	private EnumSet<NotificationType> notificationTypeEnumSet;
}
