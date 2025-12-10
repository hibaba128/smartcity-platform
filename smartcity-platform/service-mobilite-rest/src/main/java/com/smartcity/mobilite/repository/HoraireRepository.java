package com.smartcity.mobilite.repository;

import com.smartcity.mobilite.entity.Horaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraireRepository extends JpaRepository<Horaire, String> {}