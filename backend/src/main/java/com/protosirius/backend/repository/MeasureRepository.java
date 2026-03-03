package com.protosirius.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protosirius.backend.entity.Measure;

public interface MeasureRepository extends JpaRepository<Measure, Long> {

}
