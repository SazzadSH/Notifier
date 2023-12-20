package com.demo.notificationproducer.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
	@NotNull
	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	private String createdBy;
	private String createdAt;
	private Date scheduled_at;
	private Date expireAt;
	private String notificationType;
	@Column(name = "notification_from", nullable = false, updatable = true)
	private String notificationFrom;
	private String notifyCriteria;
	private String status;
	private String sms;
	private String email;
	@OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<UsersNotifications> usersNotifications;

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Notification that = (Notification) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
