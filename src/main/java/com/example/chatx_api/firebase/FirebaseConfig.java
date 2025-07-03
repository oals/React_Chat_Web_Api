package com.example.chatx_api.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.*;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() throws IOException {

        InputStream serviceAccountStream = null;

        File file = new File(firebaseConfigPath);
        if (file.exists() && file.isFile()) {
            serviceAccountStream = new FileInputStream(file);
        } else {
            serviceAccountStream = getClass().getClassLoader().getResourceAsStream(firebaseConfigPath);
            if (serviceAccountStream == null) {
                throw new IOException("Firebase config file not found in file system or classpath: " + firebaseConfigPath);
            }
        }
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        FirebaseApp.initializeApp(options);

    }
}
