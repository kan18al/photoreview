package com.example.photoReview;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer {

    private FirebaseApp defaultApp;

    @PostConstruct
    private void initDB() throws IOException {
        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("service-account-file.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://photoapp-6ea36-default-rtdb.europe-west1.firebasedatabase.app/")
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            defaultApp = FirebaseApp.initializeApp(options);
        }
    }

    public FirebaseAuth getFirebaseAuth() { return FirebaseAuth.getInstance(defaultApp); }
    public FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance(defaultApp);
    }
}
