package com.smartcity.orchestrateur.repository;

import com.smartcity.orchestrateur.entity.AlerteUrgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AlerteUrgenceRepository extends JpaRepository<AlerteUrgence, Long> {
    Optional<AlerteUrgence> findByAlertId(String alertId);
}