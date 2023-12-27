package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.NotificationStatus;
import com.demo.notificationproducer.models.enums.NotificationType;
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

	@Column(name = "created_by", length = 128)
	private String createdBy;
	@Column(name = "notification_from", length = 128)
	private String notificationFrom;

	@CreationTimestamp
	private LocalDateTime createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime scheduledAt;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime expireAt;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;
	@Column(name = "status", nullable = false)
	private NotificationStatus status;
	private Boolean email;

	@OneToMany(fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<NotifyCriteria> notifyCriteria;

	@Lob
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	private String bulkFile;

	@OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<UsersNotifications> usersNotifications;
}
