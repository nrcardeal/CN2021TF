package cnv2021tfservice.services;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;

import java.util.HashMap;

public class FirestoreService {
    public static Firestore database = null;

    public static void init(GoogleCredentials credentials) {
        FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
        database = options.getService();
    }

    public void addDocument(HashMap<String, Object> hMap, String filename) throws Exception {
        ApiFuture<WriteResult> future = database
                .collection("BlobMetadata")
                .document(filename + "-metadata")
                .create(hMap);
        future.get();
    }
}
