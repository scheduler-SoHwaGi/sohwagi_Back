package org.project.sohwagi.infra.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class FirebaseInitialization {

  @PostConstruct
  public void init() {
    try {
      InputStream serviceAccount = getClass()
          .getClassLoader()
          .getResourceAsStream("firebase-service-account.json");
      if (serviceAccount == null) {
        throw new IllegalStateException("❌ firebase-service-account.json not found in classpath!");
      }
      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .build();
      FirebaseApp.initializeApp(options);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
