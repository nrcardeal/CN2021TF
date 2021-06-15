package cnv2021tfservice.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import java.util.concurrent.TimeUnit;

public class CloudPubSubService {
    public void publishMessage(String message, String blobName) throws Exception {
        TopicName tName = TopicName.ofProjectTopicName(System.getenv("PROJECT_ID"), "topicworkers");
        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(tName).build();
            ByteString msgData = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(msgData)
                    .putAttributes(blobName + "-metadata", "value1")
                    .build();
            ApiFuture<String> future = publisher.publish(pubsubMessage);
            String msgID = future.get();
            System.out.println("Message Published with ID=" + msgID);
        } finally {
            if(publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(60, TimeUnit.SECONDS);
            }
        }
    }
}
