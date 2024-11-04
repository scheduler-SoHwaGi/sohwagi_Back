package org.project.sohwagi.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.sohwagi.schedule.dto.ScheduleRequest;

@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	private String date;

	@Column
	private Long userId;

	public Schedule (ScheduleRequest request, Long userId) {
		this.title = request.getTitle();
		this.date = request.getDate();
		this.userId = userId;
	}
}
