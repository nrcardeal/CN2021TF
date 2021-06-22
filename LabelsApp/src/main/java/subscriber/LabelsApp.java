package subscriber;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

public class LabelsApp {

    public static String PROJECT_ID = "g12-t1d-v2021";
    public static String subsName = "topicworkers-sub";
    public static void main(String[] args) {
        ProjectSubscriptionName projSubscriptionName = ProjectSubscriptionName.of(PROJECT_ID, subsName);
        Subscriber subscriber = Subscriber.newBuilder(projSubscriptionName, new MessageReceiveHandler()).build();
        subscriber.startAsync().awaitRunning();
        subscriber.awaitTerminated();
    }
}
