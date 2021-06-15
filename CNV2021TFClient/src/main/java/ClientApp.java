import cnv2021tfservice.ImageRequest;
import cnv2021tfservice.ServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ClientApp {

    //to use on local machine
    private static String svcIP = "localhost";
    //to use with vm
    //private static String svcIP = "35.246.54.43";
    private static int svcPort = 8000;
    private static ManagedChannel channel;
    private static ServiceGrpc.ServiceStub noBlockStub;

    public static void main(String[] args) {
        try {
            channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                    // Channels are secure by default (via SSL/TLS).
                    // For the example we disable TLS to avoid needing certificates.
                    .usePlaintext()
                    .build();
            noBlockStub = ServiceGrpc.newStub(channel);
            System.out.println("Please insert file's path:");
            Scanner in = new Scanner(System.in);
            String filePath = in.nextLine();
            ClientStreamObserver streamObserver = new ClientStreamObserver();
            StreamObserver<ImageRequest> contents = noBlockStub.uploadImage(streamObserver);
            Path uploadFrom = Paths.get(filePath);
            String filename = uploadFrom.getFileName().toString();
            String contentType = Files.probeContentType(uploadFrom);
            byte[] buffer = new byte[1024];
            try(InputStream input = Files.newInputStream(uploadFrom)) {
                while ((input.read(buffer)) >= 0) {
                    contents.onNext(
                            ImageRequest
                                    .newBuilder()
                                    .setImageBlockBytes(ByteString.copyFrom(buffer))
                                    .setBlobName(filename)
                                    .setContentType(contentType)
                                    .build()
                    );
                }
                contents.onCompleted();
            }
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
