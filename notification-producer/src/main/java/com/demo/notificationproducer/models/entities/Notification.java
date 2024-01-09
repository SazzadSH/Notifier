package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.NotificationStatus;
import com.demo.notificationproducer.models.enums.NotificationType;
import com.demo.notificationproducer.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
	@NotNull
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@Column(name = "notification_from", length = 128)
	private String notificationFrom;

	@CreationTimestamp
	@Column(name = "craeted_at", nullable = false)
	private LocalDateTime createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime scheduledAt;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime expiresAt;

	@Column(name = "notification_type", nullable = false, length = Constants.NOTIFICATION_TYPE_MAX_LENGTH)
	private String notificationTypes;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = Constants.NOTIFICATION_STATUS_MAX_LENGTH)
	private NotificationStatus status;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@OneToMany(mappedBy = "notification", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<NotificationTarget> notificationTargets;

	@OneToMany(mappedBy = "notification", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<UsersNotifications> usersNotifications;
}