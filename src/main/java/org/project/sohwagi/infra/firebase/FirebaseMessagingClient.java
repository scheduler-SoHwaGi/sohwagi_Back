package org.project.sohwagi.infra.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FirebaseMessagingClient {

  @Async("notificationExecutor")
  public void sendMessage(String fcmToken, String title, String body)
      throws FirebaseMessagingException {
    Message message = Message.builder()
        .putData("title", title)
        .putData("body", body)
        .setToken(fcmToken)
        .build();

    String response = FirebaseMessaging.getInstance().send(message);
    log.info(response);
  }

}
