package org.project.sohwagi.schedule.application.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.UseCase;
import org.project.sohwagi.schedule.adapter.in.web.request.ScheduleRequest;
import org.project.sohwagi.schedule.adapter.in.web.response.ScheduleResponse;
import org.project.sohwagi.schedule.application.domain.model.Schedule;
import org.project.sohwagi.schedule.application.port.in.command.CreateScheduleByTextCommand;
import org.project.sohwagi.schedule.application.port.in.command.DeleteScheduleCommand;
import org.project.sohwagi.schedule.application.port.in.query.GetScheduleListQuery;
import org.project.sohwagi.schedule.application.port.in.usecase.CreateScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.DeleteScheduleUseCase;
import org.project.sohwagi.schedule.application.port.in.usecase.GetScheduleUseCase;
import org.project.sohwagi.schedule.application.port.out.CallGptPort;
import org.project.sohwagi.schedule.application.port.out.DeleteSchedulePort;
import org.project.sohwagi.schedule.application.port.out.LoadSchedulePort;
import org.project.sohwagi.schedule.application.port.out.SaveSchedulePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Service
@RequiredArgsConstructor
public class ScheduleService
	implements CreateScheduleUseCase, GetScheduleUseCase, DeleteScheduleUseCase {

	private final CallGptPort callGptPort;
	private final SaveSchedulePort saveSchedulePort;
	private final LoadSchedulePort loadSchedulePort;
	private final DeleteSchedulePort deleteSchedulePort;

	@Override
	@Transactional
	public Long createScheduleByText(CreateScheduleByTextCommand command)
		throws JsonProcessingException {
		ScheduleRequest scheduleRequest = callGptPort.callGptForTextSchedule(command.text());

		Schedule schedule = parseDateString(scheduleRequest, command.userId());

		Schedule savedSchedule = saveSchedulePort.saveSchedule(schedule);

		return savedSchedule.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ScheduleResponse> getScheduleList(GetScheduleListQuery query) {
		List<Schedule> schedules = loadSchedulePort.loadSchedulesByUserId(query.userId());
		return schedules.stream().map(ScheduleResponse::new).toList();
	}


	@Override
	@Transactional
	public void deleteSchedule(DeleteScheduleCommand command) {
		Schedule schedule = loadSchedulePort.loadScheduleById(command.scheduleId());

		deleteSchedulePort.deleteSchedule(schedule);
	}

	private Schedule parseDateString(ScheduleRequest request, Long userId) {
		Pattern pattern = Pattern.compile("(\\d{1,2})월 (\\d{1,2})일 (\\S+)");
		Matcher matcher = pattern.matcher(request.getDate());

		if (matcher.matches()) {
			int month = Integer.parseInt(matcher.group(1));
			int day = Integer.parseInt(matcher.group(2));
			String dayOfWeek = matcher.group(3);

			return new Schedule(request.getTitle(), month, day, dayOfWeek, userId);
		} else {
			throw new IllegalArgumentException("Invalid date format: " + request.getDate());
		}
	}

}
