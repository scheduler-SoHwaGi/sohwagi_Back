package org.project.sohwagi.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.project.sohwagi.schedule.dto.ScheduleRequest;

public interface GPTService {

	ScheduleRequest gptCall(String prompt) throws JsonProcessingException;

}
