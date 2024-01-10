package com.demo.notificationproducer.repositories;

import com.demo.notificationproducer.models.entities.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<Designation, Integer> {
}
