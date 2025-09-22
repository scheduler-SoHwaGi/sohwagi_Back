package org.project.sohwagi.infra.jpa;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.project.sohwagi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<User, Long> {

  Optional<User> findByFcmToken(String fcmToken);

  Optional<User> findByUserName(String userName);

  @Query("SELECT u FROM User u WHERE u.isDeleted = false AND NOT EXISTS " +
      "(SELECT s FROM Schedule s WHERE s.userId = u.id AND " +
      "(s.year * 10000 + s.month * 100 + s.day) BETWEEN :startYmd AND :endYmd)")
  List<User> findActiveUsersWithoutSchedulesBetweenYmd(
      @Param("startYmd") int startYmd,
      @Param("endYmd") int endYmd);

}
