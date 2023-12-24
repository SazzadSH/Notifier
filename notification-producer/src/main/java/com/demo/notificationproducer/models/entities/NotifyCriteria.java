package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.UserType;
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

	private String zones;
	private String dists;
	private String subDists;
	private String policeStations;
	private String institutes;
	@Enumerated(EnumType.STRING)
	private UserType userTypes;
	private String users;
//	private String excludeZones;
//	private String excludeDists;
//	private String excludeSubDists;
//	private String excludePS;
//	private String excludeInstitutes;
//	private String excludeUserTypes;
//	private String excludeUsers;
}
