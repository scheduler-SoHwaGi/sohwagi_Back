package org.project.sohwagi.schedule.application.port.in.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.project.sohwagi.schedule.application.domain.model.Schedule;
import org.project.sohwagi.schedule.application.port.in.command.CreateScheduleByTextCommand;

public interface CreateScheduleUseCase {

	Long createScheduleByText(CreateScheduleByTextCommand command) throws JsonProcessingException;

}
