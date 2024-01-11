package com.demo.notificationproducer.repositories;

import com.demo.notificationproducer.models.entities.NotificationTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTargetRepository extends JpaRepository<NotificationTarget, Integer> {
}
