package com.demo.notificationproducer.models.entities;

import com.demo.notificationproducer.models.enums.UserType;
import com.demo.notificationproducer.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "designation")
public class Designation
{
    @Id
    private Integer id;

    @NotNull
    @Column(nullable = false, name = "name_bn")
    private String nameBangla;

    @NotNull
    @Column(nullable = false, name = "name_en")
    private String nameEnglish;

    @NotNull
    @Column(nullable = false, name = "user_type", length = Constants.USER_TYPE_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "emis_id")
    private int emisId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "last_synced_on", precision = 2)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastSyncedOn;
}
