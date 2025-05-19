package org.project.sohwagi.schedule.application.port.in.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.project.sohwagi.application.cmd.CreateScheduleByTextCommand;

public interface CreateScheduleUseCase {

	Long createScheduleByText(CreateScheduleByTextCommand command) throws JsonProcessingException;

}
