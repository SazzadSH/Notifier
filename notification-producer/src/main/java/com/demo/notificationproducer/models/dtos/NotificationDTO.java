package com.demo.notificationproducer.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class NotificationDTO {
	private String createdBy;
	@JsonIgnore
//	@PastOrPresent
	private LocalDateTime createdAt;
//	@FutureOrPresent
	private LocalDateTime scheduledAt;
//	@Future
	private LocalDateTime expireAt;
	private String notificationFrom;
	private Boolean email;
	@NotBlank(message = "Notification content is empty")
	private String content;
	@NotNull(message = "Notify Criteria is null.")
	private Set<NotifyCriteriaDTO> notifyCriteria;
}
