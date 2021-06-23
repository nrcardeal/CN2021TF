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
        String[] dataArray = data.split(":");
        String id = dataArray[0];
        String filename = msg.attributes.get("filename");
        logger.info(filename);
        String labelsArray = dataArray[1];
        List<String> labels = Arrays.asList(labelsArray.substring(1, labelsArray.length()-1).split(", "));
        List<String> translated = TranslateService.TranslateLabels(labels);
        try {
            firestore.addDocument(new HashMap<>(){{
                put("id", id);
                put("filename", filename);
                put("labels", labels);
                put("translated", translated);
                put("uploadDate", new Date());
                put("ips", Arrays.asList(dataArray[2], dataArray[3]));
            }}, id);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}