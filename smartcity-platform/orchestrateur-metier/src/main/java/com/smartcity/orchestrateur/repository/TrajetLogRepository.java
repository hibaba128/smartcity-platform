package com.smartcity.orchestrateur.repository;

import com.smartcity.orchestrateur.entity.TrajetLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrajetLogRepository extends JpaRepository<TrajetLog, Long> {
    List<TrajetLog> findByQuartier(String quartier);
    List<TrajetLog> findTop10ByOrderByCreatedAtDesc();
}