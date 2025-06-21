package org.project.sohwagi.infra.llm;

public class LlmResult {

  public record ExtractedScheduleInformation(
      String title,
      String date
  ) {

  }

}
