package com.yarzar.booking_system.core_module.repository;

import com.yarzar.booking_system.core_module.entity.ClassWaitList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassWaitListRepository extends BaseRepository<ClassWaitList, UUID> {
    boolean existsByClassesIdAndUserId(UUID classId, UUID userId);

    ClassWaitList findFirstByClassesIdOrderByWaitedAt(UUID classId);

    List<ClassWaitList> findAllByClassesId(UUID classId);
}
