package com.yarzar.booking_system.core_module.repository;

import com.yarzar.booking_system.core_module.entity.Classes;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClassesRepository extends BaseRepository<Classes, UUID> {
    List<Classes> findByEndAtBetween(Instant startOfDay, Instant endOfDay);
}
