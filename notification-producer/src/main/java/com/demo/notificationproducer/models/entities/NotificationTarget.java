package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.UserType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "notification_target")
public class NotificationTarget {
	@Setter(AccessLevel.NONE)
	@NotNull
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@ToString.Exclude
	private Notification notification;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	private Location location;

	@Column(name = "include_office")
	private Boolean includeOffice;
	@Column(name = "include_institutes")
	private Boolean includeInstitutes;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "education_institute_id")
	private EducationalInstitute educationalInstitute;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "office_id")
	private Office office;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "designation_id")
	private Designation designation;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "bulk_file")
	private String bulkFile;
}
