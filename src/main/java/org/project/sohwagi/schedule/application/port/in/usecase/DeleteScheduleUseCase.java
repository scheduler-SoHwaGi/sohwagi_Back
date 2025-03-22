package org.project.sohwagi.schedule.application.port.in.usecase;

import org.project.sohwagi.schedule.application.port.in.command.DeleteScheduleCommand;

public interface DeleteScheduleUseCase {

	void deleteSchedule(DeleteScheduleCommand command);

}
