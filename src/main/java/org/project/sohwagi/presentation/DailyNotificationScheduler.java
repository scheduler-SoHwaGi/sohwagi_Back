package org.project.sohwagi.presentation;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.project.sohwagi.application.facade.ScheduleNotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyNotificationScheduler {

  private final ScheduleNotificationService scheduleNotificationService;

  public DailyNotificationScheduler(ScheduleNotificationService scheduleNotificationService){
    this.scheduleNotificationService = scheduleNotificationService;
  }

  @Scheduled(cron = "0 00 09 * * *", zone = "Asia/Seoul")
  public void notifyTodaySchedules() throws FirebaseMessagingException {
    scheduleNotificationService.sendDailyScheduleNotifications();
  }

  @Scheduled(cron = "0 47 12 * * WED", zone = "Asia/Seoul")
  public void notifyScheduleRegistrationPrompt() throws FirebaseMessagingException {
    scheduleNotificationService.sendScheduleRegistrationNotifications();
  }
}
