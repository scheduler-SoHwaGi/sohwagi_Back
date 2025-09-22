package org.project.sohwagi.application.facade;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.application.service.ScheduleService;
import org.project.sohwagi.application.service.UserService;
import org.project.sohwagi.domain.Schedule;
import org.project.sohwagi.domain.User;
import org.project.sohwagi.infra.firebase.FirebaseMessagingClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleNotificationService {

  private final ScheduleService scheduleService;
  private final FirebaseMessagingClient firebaseMessagingClient;
  private final UserService userService;

  public ScheduleNotificationService(ScheduleService scheduleService,
      FirebaseMessagingClient firebaseMessagingClient, UserService userService) {
    this.scheduleService = scheduleService;
    this.firebaseMessagingClient = firebaseMessagingClient;
    this.userService = userService;
  }

  public void sendDailyScheduleNotifications() throws FirebaseMessagingException {
    LocalDate today = LocalDate.now();

    List<Schedule> todaySchedules = scheduleService.findTodaySchedules(today);
    if (todaySchedules.isEmpty()) {
      return;
    }

    Map<Long, List<Schedule>> scheduleMap = todaySchedules.stream()
        .collect(Collectors.groupingBy(Schedule::getUserId));

    for (Map.Entry<Long, List<Schedule>> entry : scheduleMap.entrySet()) {
      Long userId = entry.getKey();
      List<Schedule> schedules = entry.getValue();
      User user = userService.findById(userId);
      if(user.getFcmToken() == null || user.getFcmToken().isEmpty()) {
        continue;
      }

      String title = String.format("오늘 일정 %d개다 햄+_+", schedules.size());
      String body = String.format(
          "오늘 %s %02d:%02d에 %s이(가) 있어요!",
          schedules.get(0).getAmPm(),
          schedules.get(0).getHour(),
          schedules.get(0).getMinute(),
          schedules.get(0).getTitle());

      firebaseMessagingClient.sendMessage(user.getFcmToken(), title, body);
    }
  }
}
