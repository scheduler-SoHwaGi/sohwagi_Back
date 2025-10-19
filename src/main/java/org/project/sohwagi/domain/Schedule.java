package org.project.sohwagi.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Internal;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.project.sohwagi.common.exception.CustomException;
import org.project.sohwagi.common.exception.ErrorCode;

@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE schedule SET deleted_at = NOW() WHERE id = ?")
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private int month;

  @Column
  private int day;

  @Column
  private String dayOfWeek;

  @Column
  private Long userId;

  @Column
  @ColumnDefault("NULL")
  private LocalDateTime deletedAt;

  @Column
  private String amPm;

  @Column
  private Integer hour;

  @Column
  private Integer minute;

  @Column
  private int year;

  @Column
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column
  private Boolean checked;

  @Column
  @Enumerated(EnumType.STRING)
  private ScheduleType type;

  public Schedule(
    String title,
    Long userId,
    Integer year,
    Integer month,
    Integer day,
    String dayOfWeek,
    String amPm,
    Integer hour,
    Integer minute,
    ScheduleType type) {
    validateTitle(title);
    applyDateRules(year, month, day, amPm, hour, minute, dayOfWeek);
    this.title = title;
    this.userId = userId;
    this.checked = false;
    this.type = type;
  }

  public void checkSchedule(boolean checked) {
    this.checked = !checked;
  }

  public boolean isChecked() {
    return checked;
  }

  private void validateTitle(String title) {
    if (title == null) {
      throw new CustomException(ErrorCode.INVALID_SCHEDULE_TEXT_INPUT_VALUE);
    }
  }

  /**
   * 도메인 규칙 적용:
   * - 날짜 X, 시간 X → 등록 시점 날짜 & 오전 9:00
   * - 날짜 X, 시간 O → 등록 시점 날짜
   * - 날짜 O → 그대로 사용
   */
  private void applyDateRules(
    Integer year, Integer month, Integer day, String amPm, Integer hour, Integer minute, String dayOfWeek) {
    LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
    boolean dateMissing = (year == null || month == null || day == null);
    boolean timeMissing = (hour == null && minute == null);

    if (dateMissing && timeMissing) {
      this.year = now.getYear();
      this.month = now.getMonthValue();
      this.day = now.getDayOfMonth();
      this.dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    } else if (dateMissing && !timeMissing) {
      this.year = now.getYear();
      this.month = now.getMonthValue();
      this.day = now.getDayOfMonth();
      this.dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
      this.hour = hour;
      this.minute = minute;
      this.amPm = amPm;
    } else {
      this.year = year;
      this.month = month;
      this.day = day;
      this.hour = hour;
      this.minute = minute;
      this.amPm = amPm;
      this.dayOfWeek = dayOfWeek;
    }
  }
}