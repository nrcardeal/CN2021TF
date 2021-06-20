package subscriber;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;

import java.util.List;

public class MessageReceiveHandler implements MessageReceiver {
    @Override
    public void receiveMessage(PubsubMessage msg, AckReplyConsumer ackReply) {
        String message = msg.getData().toStringUtf8();
        try {
            String[] fields = message.split(" ");
            String id = fields[0];
            String bucketName = fields[1];
            String blobName = fields[2];
            List<String> labels = DetectService.detectLabels(bucketName, blobName);
            PubSubService.publishMessage(id + ':' + labels, blobName);
            ackReply.ack();
        } catch (Exception e) {
            e.printStackTrace();
            ackReply.nack();
        }
    }
}