package com.demo.notificationproducer.repositories;

import com.demo.notificationproducer.models.entities.EducationalInstitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalInstituteRepository extends JpaRepository<EducationalInstitute, Integer> {
}
