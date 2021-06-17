import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Function implements BackgroundFunction<Message> {
    private static final Logger logger = Logger.getLogger(Function.class.getName());
    private FirestoreService firestore;

    public Function() {
        try {
            firestore = FirestoreService.init(GoogleCredentials.getApplicationDefault());
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void accept(Message msg, Context context) {
        String data = new String(Base64.getDecoder().decode(msg.data));
        logger.info(data);
        String id = data.split(":")[0];
        String lbls = data.split(":")[1];
        List<String> labels = Arrays.asList(lbls.substring(1, lbls.length()-1).split(", "));
        List<String> translated = TranslateService.TranslateLabels(labels);
        try {
            firestore.addDocument(new HashMap<>(){{
                put("id", id);
                put("labels", labels);
                put("translated", translated);
                put("uploaded", new Date().toString());
            }}, id);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}