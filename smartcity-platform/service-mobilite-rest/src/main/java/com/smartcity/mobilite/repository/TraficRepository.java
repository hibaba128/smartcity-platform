package com.smartcity.mobilite.repository;

import com.smartcity.mobilite.entity.Trafic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraficRepository extends JpaRepository<Trafic, String> {}