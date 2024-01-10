package com.demo.notificationproducer.repositories;

import com.demo.notificationproducer.models.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Integer> {
}
