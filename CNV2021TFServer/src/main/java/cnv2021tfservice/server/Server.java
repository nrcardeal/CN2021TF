package cnv2021tfservice.server;

import cnv2021tfservice.*;
import cnv2021tfservice.exceptions.DateFormatException;
import cnv2021tfservice.exceptions.DocumentNotFoundException;
import cnv2021tfservice.observers.ServerObserver;
import cnv2021tfservice.services.CloudStorageService;
import cnv2021tfservice.services.FirestoreService;
import com.google.auth.oauth2.GoogleCredentials;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class Server extends ServiceGrpc.ServiceImplBase {
    public static int svcPort = Integer.parseInt(System.getenv("PORT"));
    private final FirestoreService firestoreService = new FirestoreService();

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
        try {
            firestoreService.addLabelsAndTranslations(request.getId());
            responseObserver.onNext(Labels.newBuilder()
                    .addAllLabels(firestoreService.originals)
                    .addAllLabelsTranslations(firestoreService.translated)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception | DocumentNotFoundException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void filterFiles(FilterRequest request, StreamObserver<FilterResult> responseObserver) {
        try {
            responseObserver.onNext(FilterResult.newBuilder()
                    .addAllFilename(firestoreService
                            .getFilteredFilesName(
                                    new SimpleDateFormat("dd/MM/yyyy").parse(request.getInitDate()),
                                    new SimpleDateFormat("dd/MM/yyyy").parse(request.getEndDate()),
                                    request.getLabel())).build());
            responseObserver.onCompleted();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            responseObserver.onError(new DateFormatException("Date Format Incorrect."));
        }
    }
}
