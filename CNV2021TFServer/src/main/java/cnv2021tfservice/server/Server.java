package cnv2021tfservice.server;

import cnv2021tfservice.*;
import cnv2021tfservice.observers.ServerObserver;
import cnv2021tfservice.services.CloudStorageService;
import cnv2021tfservice.services.FirestoreService;
import com.google.auth.oauth2.GoogleCredentials;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;

public class Server extends ServiceGrpc.ServiceImplBase {
    public static int svcPort = Integer.parseInt(System.getenv("PORT"));

    public static void main(String[] args) {
        io.grpc.Server svc = null;
        try {
            svc = ServerBuilder.forPort(svcPort).addService(new Server()).build();
            svc.start();
            System.out.println("Server started, listening on " + svcPort);
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            CloudStorageService.init(credentials);
            FirestoreService.init(credentials);
            svc.awaitTermination();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (svc != null) svc.shutdown();
        }
    }

    @Override
    public StreamObserver<ImageRequest> uploadImage(StreamObserver<ImageResult> responseObserver) {
        return new ServerObserver(responseObserver);
    }

    @Override
    public void getLabelsList(ImageResult request, StreamObserver<Labels> responseObserver) {
        super.getLabelsList(request, responseObserver);
    }

    @Override
    public void filterFiles(FilterRequest request, StreamObserver<FilterResult> responseObserver) {
        super.filterFiles(request, responseObserver);
    }
}
