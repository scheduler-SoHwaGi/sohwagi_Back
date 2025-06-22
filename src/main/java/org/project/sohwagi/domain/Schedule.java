package org.project.sohwagi.domain;

import jakarta.persistence.*;

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
	private int hour;

	@Column
	private int minute;

	@Column
	private int year;

	@Column
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column
	private Boolean checked;

	public Schedule(
			String title,
			Long userId,
			int year,
			int month,
			int day,
			String dayOfWeek,
			String amPm,
			int hour,
			int minute) {
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
	}

	public void checkSchedule(boolean checked) {
		this.checked =!checked;
	}
}