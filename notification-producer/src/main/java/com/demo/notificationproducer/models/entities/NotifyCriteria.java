package com.demo.notificationproducer.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "notify_criteria")
public class NotifyCriteria {
	@Setter(AccessLevel.NONE)
	@NotNull
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notifications_id")
	@ToString.Exclude
	private Notification notification;

	private String includeZones;
	private String includeDists;
	private String includeSubDists;
	private String includePS;
	private String includeInstitutes;
	private String includeUserTypes;
	private String includeUsers;
	private String excludeZones;
	private String excludeDist;
	private String excludeSubDist;
	private String excludePS;
	private String excludeInstitutes;
	private String excludeUserTypes;
	private String excludeUsers;
}
