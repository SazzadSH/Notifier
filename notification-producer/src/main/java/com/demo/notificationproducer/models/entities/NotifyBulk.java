//package com.demo.notificationproducer.models.entities;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//import java.util.Date;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@RequiredArgsConstructor
//@ToString
//@Entity
//@Table(name = "notify_bulk")
//public class NotifyBulk {
//	@Setter(AccessLevel.NONE)
//	@NotNull
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@Column(name = "id", nullable = false, updatable = false)
//	private Long id;
//	private UUID csvId;
//	private String fileName;
//	private String filePath;
//	private String checksum;
//	private String createdBy;
//	private Date createdAt;
//}
