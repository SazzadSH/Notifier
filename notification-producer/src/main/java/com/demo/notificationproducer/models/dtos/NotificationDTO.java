package com.demo.notificationproducer.models.dtos;

import com.demo.notificationproducer.models.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class NotificationDTO {
	private String createdBy;
	@JsonIgnore
	@PastOrPresent
	private LocalDateTime createdAt;
	@FutureOrPresent
	private LocalDateTime scheduledAt;
	@Future
	private LocalDateTime expireAt;
	private String notificationFrom;
	private Boolean email;
	@NotNull
	@NotEmpty
	@NotBlank
	private String content;
	@NotNull
	private Set<NotifyCriteriaDTO> notifyCriteria;
}
