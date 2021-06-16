package subscriber;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.pubsub.v1.PubsubMessage;
import io.grpc.Context;

import java.io.IOException;
import java.util.List;

public class MessageReceiveHandler implements MessageReceiver {
    @Override
    public void receiveMessage(PubsubMessage msg, AckReplyConsumer ackReply) {
        String message = msg.getData().toStringUtf8();
        try {
            String bucketName = message.split(" ")[1];
            String blobName = message.split(" ")[2];
            List<String> labels = DetectService.detectLabels(bucketName, blobName);
            PubSubService.publishMessage(blobName + '-' + labels, blobName);
            ackReply.ack();
        } catch (Exception e) {
            e.printStackTrace();
            ackReply.nack();
        }
    }
}