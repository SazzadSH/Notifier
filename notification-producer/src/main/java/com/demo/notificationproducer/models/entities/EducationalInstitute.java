package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.EducationalInstituteType;
import com.demo.notificationproducer.models.enums.ISASCategory;
import com.demo.notificationproducer.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "educational_institute")
public class EducationalInstitute
{
	@Id
	@Column(nullable = false, name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "zone_id")
	private Location zone;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "district_id")
	private Location district;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "upazila_id")
	private Location upazila;

	@NotNull
	@Column(nullable = false, name = "eiin", length = 15)
	private String eiin;

	@NotNull
	@Column(nullable = false, name = "branch_bn", length = 50)
	@ColumnDefault("প্রধান শাখা")
	private String branchNameBangla;

	@NotNull
	@Column(nullable = false, name = "branch_en", length = 50)
	@ColumnDefault("Main Branch")
	private String branchNameEnglish;

	@NotNull
	@Column(nullable = false, name = "isas_category", length = 8)
	@Enumerated(EnumType.STRING)
	private ISASCategory isasCategory;

	@NotNull
	@Column(nullable = false, name = "type", length = 20)
	@Enumerated(EnumType.STRING)
	private EducationalInstituteType type;

	@NotNull
	@Column(nullable = false, name = "number_of_buildings")
	private Integer numberOfBuildings;

	@NotNull
	@Column(nullable = false, name = "name_bn")
	private String nameBangla;

	@NotNull
	@Column(nullable = false, name = "name_en")
	private String nameEnglish;

	@NotNull
	@Column(nullable = false, name = "emis_id")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer emisId;

	@NotNull
	@Column(nullable = false, name = "location_path", length = Constants.PATH_MAX_LENGTH)
	private String locationPath;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Column(name = "last_synced_on", precision = 2)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastSyncedOn;

	@OneToMany(mappedBy = "educationalInstitute")
	private Set<EducationalInstitutionGeoData> geoData;
}
