package com.smartcity.mobilite.repository;

import com.smartcity.mobilite.entity.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, String> {}