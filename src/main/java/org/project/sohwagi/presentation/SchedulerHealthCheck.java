package org.project.sohwagi.presentation;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerHealthCheck {

  private static final Logger log = LoggerFactory.getLogger(SchedulerHealthCheck.class);

  @PostConstruct
  public void init() {
    log.info("✅ SchedulerHealthCheck bean successfully initialized!");
  }

  @Scheduled(fixedRate = 60000)
  public void verifySchedulerRunning() {
    log.info("⏰ SchedulerHealthCheck is running — scheduling works fine!");
  }

}
