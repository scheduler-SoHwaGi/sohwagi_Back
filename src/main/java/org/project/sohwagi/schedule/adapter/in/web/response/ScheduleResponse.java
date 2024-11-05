package org.project.sohwagi.schedule.adapter.in.web.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.sohwagi.schedule.application.domain.model.Schedule;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScheduleResponse {

	private Long scheduleId;

	private String title;

	private int month;

	private int day;

	private String dayOfWeek;

	public ScheduleResponse(Schedule schedule) {
		this.scheduleId = schedule.getId();
		this.title = schedule.getTitle();
		this.month = schedule.getMonth();
		this.day = schedule.getDay();
		this.dayOfWeek = schedule.getDayOfWeek();
	}

}
