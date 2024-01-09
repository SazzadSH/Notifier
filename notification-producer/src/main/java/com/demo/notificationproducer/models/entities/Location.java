package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.LocationType;
import com.demo.notificationproducer.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "location")
public class Location
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, name = "bbs_code", length = 20)
	private String bbsCode;

	@NotNull
	@Column(nullable = false, name = "path", length = Constants.PATH_MAX_LENGTH)
	private String path;

	@Column(name = "parent_id", insertable = false, updatable = false)
	private Integer parentLocationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Location parentLocation;

	@NotNull
	@Column(nullable = false, name = "name_en", length = 64)
	private String nameEnglish;

	@NotNull
	@Column(nullable = false, name = "name_bn", length = 64)
	private String nameBangla;

	@NotNull
	@Column(nullable = false, name = "type", length = 10)
	@Enumerated(EnumType.STRING)
	private LocationType type;

	@Column(nullable = false, name = "emis_id")
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer emisId;


	@Override
	public String toString()
	{
		return "%s (%s:%s)".formatted(getNameEnglish(), type, path);
	}
}