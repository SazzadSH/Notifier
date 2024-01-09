package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.OfficeType;
import com.demo.notificationproducer.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "office")
public class Office
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "zone_id")
	private Location zone;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	private Location district;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "upazila_id")
	private Location upazila;

	@NotNull
	@Column(nullable = false, name = "location_path", length = Constants.PATH_MAX_LENGTH)
	private String locationPath;

	@NotNull
	@Column(nullable = false, name = "type", length = Constants.OFFICE_TYPE_MAX_LENGTH)
	@Enumerated(EnumType.STRING)
	private OfficeType type;

	@NotNull
	@Column(nullable = false, name = "staff_count")
	private Integer staffCount;

	@NotNull
	@Column(nullable = false, name = "name_bn")
	private String nameBn;

	@NotNull
	@Column(nullable = false, name = "name_en")
	private String nameEn;

	@NotNull
	@Column(nullable = false, name = "emis_id")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer emisId;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Column(name = "last_synced_on", precision = 2)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastSyncedOn;
}
