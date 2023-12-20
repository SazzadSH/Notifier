package com.demo.notificationproducer.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class UsersNotificationKey implements Serializable {
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "notification_id")
	private Long notificationId;
}