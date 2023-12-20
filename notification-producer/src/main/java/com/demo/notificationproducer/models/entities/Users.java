package com.demo.notificationproducer.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
	@Column(name = "email", nullable = false)
	private String emailAddress;
	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<UsersNotifications> usersNotifications;
}
