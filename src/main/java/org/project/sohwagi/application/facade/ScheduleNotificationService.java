package org.project.sohwagi.application.facade;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.project.sohwagi.application.service.ScheduleService;
import org.project.sohwagi.application.service.UserService;
import org.project.sohwagi.domain.Schedule;
import org.project.sohwagi.domain.User;
import org.project.sohwagi.infra.firebase.FirebaseMessagingClient;
import org.springframework.stereotype.Service;

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

  public void notifyUsersOfTodaySchedules() throws FirebaseMessagingException {
    LocalDate today = LocalDate.now();

    List<Schedule> todaySchedules = scheduleService.findTodaySchedules(today);

    Map<Long, List<Schedule>> scheduleMap = todaySchedules.stream()
        .collect(Collectors.groupingBy(Schedule::getUserId));

    for (Map.Entry<Long, List<Schedule>> entry : scheduleMap.entrySet()) {
      Long userId = entry.getKey();
      List<Schedule> schedules = entry.getValue();
      User user = userService.findById(userId);

      String title = "오늘의 일정";
      String body = schedules.stream()
          .map(Schedule::getTitle)
          .collect(Collectors.joining(", "));

      firebaseMessagingClient.sendMessage(user.getFcmToken(), title, body);
    }
  }
}
