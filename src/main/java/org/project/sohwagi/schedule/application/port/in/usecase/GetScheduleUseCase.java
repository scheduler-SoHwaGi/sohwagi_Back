package org.project.sohwagi.schedule.application.port.in.usecase;

import java.util.List;
import org.project.sohwagi.schedule.adapter.in.web.response.ScheduleResponse;
import org.project.sohwagi.schedule.application.port.in.query.GetScheduleListQuery;

public interface GetScheduleUseCase {

	List<ScheduleResponse> getScheduleList(GetScheduleListQuery query);

}
