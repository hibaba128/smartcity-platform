package com.smartcity.qualiteair.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.qualiteair.entity.AirQuality;
import java.util.Optional;

@Repository
public interface AirQualityRepository extends JpaRepository<AirQuality, Long> {
    Optional<AirQuality> findByQuartier(String quartier);
}