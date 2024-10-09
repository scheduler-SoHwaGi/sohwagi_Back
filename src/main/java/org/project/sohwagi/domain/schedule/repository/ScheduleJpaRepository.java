package org.project.sohwagi.domain.schedule.repository;

import java.util.List;
import org.project.sohwagi.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findAllByUserId(Long userId);

}
