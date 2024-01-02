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
public class NotifyCriteriaDTO {
	private String location;
	private Boolean includeOffices;
	private Boolean includeInstitutes;
	private String office;
	private String institute;
	private UserType userTypes;
	private String pdsId;
}
