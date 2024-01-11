package com.demo.notificationproducer.repositories;

import com.demo.notificationproducer.models.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepositoty extends JpaRepository<Location, Integer> {
}
