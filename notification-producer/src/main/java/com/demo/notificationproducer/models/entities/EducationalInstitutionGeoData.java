package com.demo.notificationproducer.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ei_geo_data")
public class EducationalInstitutionGeoData
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "educational_institute_id")
	private EducationalInstitute educationalInstitute;

	@Column(name = "building_identifier", length = 30)
	private String buildingIdentifier;

	@NotNull
	@Column(nullable = false, name = "latitude")
	private Double latitude;

	@NotNull
	@Column(nullable = false, name = "longitude")
	private Double longitude;
}
