package org.project.sohwagi.schedule.adapter.in.web.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.sohwagi.schedule.application.domain.model.Schedule;

import java.util.List;

public class ScheduleResponse {

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	public static class ScheduleDetailResponse {

		private Long scheduleId;

		private String title;

		private int month;

		private int day;

		private String dayOfWeek;

		private String time;

		public ScheduleDetailResponse(Schedule schedule) {
			this.scheduleId = schedule.getId();
			this.title = schedule.getTitle();
			this.month = schedule.getMonth();
			this.day = schedule.getDay();
			this.dayOfWeek = schedule.getDayOfWeek();
			this.time = schedule.getAmPm() + " "
					+ schedule.getHour() + "시 "
					+ String.format("%02d", schedule.getMinute()) + "분";
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class WeekGroupedScheduleResponse {
		private String week;
		private String periodOfWeek;
		private List<ScheduleDetailResponse> schedules;
	}

}
