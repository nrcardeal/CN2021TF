package cnv2021tfservice.services;

import cnv2021tfservice.exceptions.DocumentNotFoundException;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirestoreService {
    public static Firestore db = null;
    public List<String> originals;
    public List<String> translated;

    public static void init(GoogleCredentials credentials) {
        FirestoreOptions options = FirestoreOptions.newBuilder().setCredentials(credentials).build();
        db = options.getService();
    }

    public void addLabelsAndTranslations(String id) throws DocumentNotFoundException {
        try {
            DocumentReference docRef = db.collection("image-labels").document(id + "-metadata");
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                originals = document.get("labels", List.class);
                translated = document.get("translated", List.class);
            }
            else {
                throw new DocumentNotFoundException("Document does not exist.");
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFilteredFilesName(Date initDate, Date endDate, String label) throws ExecutionException, InterruptedException {
        List<String> res = new ArrayList<>();
        Query query = db.collection("image-labels")
                .whereGreaterThanOrEqualTo("uploadDate", initDate)
                .whereLessThanOrEqualTo("uploadDate", endDate)
                .whereArrayContains("translated", label);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot doc: querySnapshot.get().getDocuments())
            res.add(doc.getString("filename"));
        return res;
    }
}
