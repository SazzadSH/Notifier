package com.demo.notificationproducer.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notifications {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	private Date createdAt;


	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Notifications that = (Notifications) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
