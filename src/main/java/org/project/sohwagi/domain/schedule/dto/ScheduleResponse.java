package org.project.sohwagi.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.sohwagi.domain.schedule.entity.Schedule;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScheduleResponse {

	private Long scheduleId;

	private String title;

	private String date;

	public ScheduleResponse(Schedule schedule) {
		this.scheduleId = schedule.getId();
		this.title = schedule.getTitle();
		this.date = schedule.getDate();
	}

}
