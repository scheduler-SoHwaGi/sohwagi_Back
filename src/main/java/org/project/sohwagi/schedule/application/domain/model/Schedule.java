package org.project.sohwagi.schedule.application.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.project.sohwagi.schedule.adapter.in.web.request.ScheduleRequest;

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

	public Schedule (String title, int month, int day, String dayOfWeek, Long userId) {
		this.title = title;
		this.month = month;
		this.day = day;
		this.dayOfWeek = dayOfWeek;
		this.userId = userId;
	}
}
