package org.project.sohwagi.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.sohwagi.domain.schedule.dto.ScheduleRequest;

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

	public Schedule (ScheduleRequest request) {
		this.title = request.getTitle();
		this.date = request.getDate();
	}
}