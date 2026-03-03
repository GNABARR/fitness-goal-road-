package com.protosirius.backend.repository;

import com.protosirius.backend.entity.Motd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotdRepository extends JpaRepository<Motd, Long> {
}
