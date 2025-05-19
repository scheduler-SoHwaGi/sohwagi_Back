package org.project.sohwagi.infra.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class FirebaseInitialization {

  @PostConstruct
  public void init() {
    try {
      FileInputStream serviceAccount = new FileInputStream(
          "src/main/resources/firebase-service-account.json");

      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .build();

      FirebaseApp.initializeApp(options);
    }catch (IOException e) {
      e.printStackTrace();
    }
  }

}
