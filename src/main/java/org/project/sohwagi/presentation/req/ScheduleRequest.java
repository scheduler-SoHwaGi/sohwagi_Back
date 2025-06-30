package org.project.sohwagi.presentation.req;

import org.project.sohwagi.application.cmd.ScheduleCommand.ScheduleCreateByTextCommand;

public class ScheduleRequest {

  public record ScheduleCreateByTextRequest(String text) { }

}
