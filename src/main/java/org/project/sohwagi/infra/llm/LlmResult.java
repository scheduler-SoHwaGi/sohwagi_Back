package org.project.sohwagi.infra.llm;

public class LlmResult {

  public record Extract_Schedule_Information(
      String title,
      String date
  ) {

  }

}
