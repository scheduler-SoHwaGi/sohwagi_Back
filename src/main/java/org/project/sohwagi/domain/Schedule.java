package org.project.sohwagi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
			int year,
			int month,
			int day,
			String dayOfWeek,
			String amPm,
			Integer hour,
			Integer minute,
			ScheduleType type) {
		this.title = title;
		this.month = month;
		this.day = day;
		this.dayOfWeek = dayOfWeek;
		this.userId = userId;
		this.hour = hour;
		this.minute = minute;
		this.year = year;
		this.amPm = amPm;
		this.checked = false;
		this.type = type;
	}

	public void checkSchedule(boolean checked) {
		this.checked =!checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public LocalDate getDate() {
		return LocalDate.of(this.year, this.month, this.day);
	}
}