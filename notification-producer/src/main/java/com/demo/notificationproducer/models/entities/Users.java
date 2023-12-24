package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.DeviceType;
import com.demo.notificationproducer.models.enums.UserStatus;
import com.demo.notificationproducer.models.enums.UserType;
import com.demo.notificationproducer.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@NotNull
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "username", length = 128)
	private String userName;

	@Column(name = "password")
	private String password;

	@NotNull
	@Column(nullable = false, name = "name_en")
	private String nameEn;

	@NotNull
	@Column(nullable = false, name = "name_bn")
	private String nameBn;

	@Column(name = "pds_id", length = 15)
	private String pdsId;

	@NotNull
	@Column(nullable = false, name = "type", length = Constants.USER_TYPE_MAX_LENGTH)
	@Enumerated(EnumType.STRING)
	private UserType type;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "designation_id")
	private Designation designation;

	@NotNull
	@Column(nullable = false, name = "status", length = 20)
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	private LocalDate dateOfBirth;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phoneNo;

	@Column(name = "emis_id")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer emisId;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Column(name = "last_synced_on", precision = 2)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastSyncedOn;

	@NotNull
	@Column(name = "last_used_device_type", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private DeviceType lastUsedMobileDeviceType;

	@NotNull
	@Column(name = "security_stamp", nullable = false, length = 36)
	private String securityStamp;

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<UsersNotifications> usersNotifications;
}
