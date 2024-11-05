package org.project.sohwagi.schedule.application.port.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.project.sohwagi.schedule.adapter.in.web.request.ScheduleRequest;

public interface CallGptPort {

	ScheduleRequest callGptForTextSchedule(String prompt) throws JsonProcessingException;

}
