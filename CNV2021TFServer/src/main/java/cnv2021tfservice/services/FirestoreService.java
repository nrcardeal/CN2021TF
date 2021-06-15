package cnv2021tfservice.services;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import java.util.concurrent.ExecutionException;

public class FirestoreService {
    public static Firestore db = null;
    public String[] originals;
    public String[] translated;

    public static void init(GoogleCredentials credentials) {
        FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
        db = options.getService();
    }

    public void addLabelsAndTranslations(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("image-labels").document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        originals = document.get("location.labels", String[].class);
        translated = document.get("location.translated", String[].class);

    }
}
