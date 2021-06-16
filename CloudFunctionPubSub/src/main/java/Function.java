import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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
        String name = data.split("-")[0];
        String[] labels = data.split("-")[1].substring(1, data.length()-1).split(", ");
        String[] translated = TranslateService.TranslateLabels(labels);
        try {
            firestore.addDocument(new HashMap<>(){{
                put("labels", labels);
                put("translated", translated);
                put("uploaded", msg.publishTime);
            }}, name);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
class Message {
    String data;
    Map<String, String> attributes;
    String messageId;
    String publishTime;
}