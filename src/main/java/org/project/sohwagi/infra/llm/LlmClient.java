package org.project.sohwagi.infra.llm;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.project.sohwagi.infra.llm.LlmResult.ExtractedScheduleInformation;

public interface LlmClient {
  LlmResult extractScheduleInformation(String input)
      throws JsonProcessingException;
}
