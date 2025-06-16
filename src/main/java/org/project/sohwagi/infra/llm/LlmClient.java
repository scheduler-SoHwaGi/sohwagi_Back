package org.project.sohwagi.infra.llm;

public interface LlmClient {
  LlmResult.Extract_Schedule_Information extractScheduleInformation(String input);
}
