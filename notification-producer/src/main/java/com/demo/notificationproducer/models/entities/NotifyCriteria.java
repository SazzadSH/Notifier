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

	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	private Notification notification;

	private String location;
	private Boolean includeOffice;
	private Boolean includeInstitutes;
	private String institute;
	private String office;
	@Enumerated(EnumType.STRING)
	private UserType userType;
	private String pdsId;
}
