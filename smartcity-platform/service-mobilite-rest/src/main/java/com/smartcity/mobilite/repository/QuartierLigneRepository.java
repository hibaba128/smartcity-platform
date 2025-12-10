package com.smartcity.mobilite.repository;

import com.smartcity.mobilite.entity.QuartierLigne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartierLigneRepository extends JpaRepository<QuartierLigne, String> {}
