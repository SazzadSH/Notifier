package com.demo.notificationproducer.models.dtos;

import com.demo.notificationproducer.models.enums.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class NotificationTargetDTO {
	private Integer location;
	private Boolean includeOffices;
	private Boolean includeInstitutes;
	private Integer office;
	private Integer institute;
	private UserType userType;
	private Integer designation;
	private Integer user;
	private String bulkFile;
}