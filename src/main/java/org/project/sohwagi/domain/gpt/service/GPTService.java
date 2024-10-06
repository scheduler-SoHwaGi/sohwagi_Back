package org.project.sohwagi.domain.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.project.sohwagi.domain.schedule.dto.ScheduleRequest;

public interface GPTService {

	ScheduleRequest gptCall(String prompt) throws JsonProcessingException;

}
