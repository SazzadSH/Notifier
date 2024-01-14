package com.demo.notificationproducer.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users_notifications")
public class UsersNotifications {
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@EmbeddedId
	private UsersNotificationKey id;
	@Setter(AccessLevel.NONE)
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("notificationId")
	@JoinColumn(name = "notification_id")
	private Notification notification;
	@Setter(AccessLevel.NONE)
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;
	private Boolean deliveryStatus;
}
