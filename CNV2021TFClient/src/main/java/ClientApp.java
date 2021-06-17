import cnv2021tfservice.ImageRequest;
import cnv2021tfservice.FilterRequest;
import cnv2021tfservice.ImageResult;
import cnv2021tfservice.ServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
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
            while (true) {
                try {
                    int option = Menu();
                    switch (option) {
                        case 0:
                            UploadImage();
                            break;
                        case 1:
                            ListLabels();
                            break;
                        case 2:
                            FilterFiles();
                            break;
                        case 99:
                            System.exit(0);
                    }
                } catch (Exception ex) {
                    System.out.println("Error executing operations!");
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static int Menu() {
        Scanner scan = new Scanner(System.in);
        int option;
        do {
            System.out.println("######## MENU ##########");
            System.out.println("Operations:");
            System.out.println(" 0: Upload an Image");
            System.out.println(" 1: List Image's Labels and Translations");
            System.out.println(" 2: Filter Files by Label");
            System.out.println("..........");
            System.out.println("99: Exit");
            System.out.print("Enter an Option:");
            option = scan.nextInt();
        } while (!((option >= 0 && option <= 5) || option == 99));
        return option;
    }

    static void UploadImage() throws IOException {
        System.out.println("Please insert file's path:");
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        ClientStreamObserver streamObserver = new ClientStreamObserver();
        StreamObserver<ImageRequest> contents = noBlockStub.uploadImage(streamObserver);
        Path uploadFrom = Paths.get(filePath);
        String filename = uploadFrom.getFileName().toString();
        String contentType = Files.probeContentType(uploadFrom);
        byte[] buffer = new byte[1024];
        try (InputStream input = Files.newInputStream(uploadFrom)) {
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
            System.out.println(streamObserver.results.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void ListLabels(){
        System.out.println("Please insert file Id:");
        Scanner in = new Scanner(System.in);
        String fileId = in.nextLine();
        ImageResult imageResult = ImageResult.newBuilder()
                .setId(fileId)
                .build();
        LabelsStreamObserver labelsObserver = new LabelsStreamObserver();
        noBlockStub.getLabelsList(imageResult, labelsObserver);
        while (!labelsObserver.isCompleted) ;
        if (labelsObserver.success) {
            System.out.println("Labels:");
            for (String label: labelsObserver.labels.getLabelsList()) {
                System.out.println(label);
            }
            System.out.println("Características:");
            for (String label: labelsObserver.labels.getLabelsTranslationsList()) {
                System.out.println(label);
            }
        }


    }

    static void FilterFiles() {
        System.out.println("Filter's initial date?");
        Scanner in = new Scanner(System.in);
        String initDate = in.nextLine();
        System.out.println("Filter's end date?");
        in = new Scanner(System.in);
        String endDate = in.nextLine();
        System.out.println("Filter's label");
        in = new Scanner(System.in);
        String label = in.nextLine();
        FilterRequest request = FilterRequest
                .newBuilder()
                .setInitDate(initDate)
                .setEndDate(endDate)
                .setLabel(label)
                .build();
        FilterStreamObserver filterObserver = new FilterStreamObserver();
        noBlockStub.filterFiles(request, filterObserver);
        while (!filterObserver.isCompleted);
        if (filterObserver.success) {
            System.out.println("Filtered Files:");
            for ( String name: filterObserver.result.getFilenameList()) {
                System.out.println(name);
            }
            in = new Scanner(System.in);
            in.nextLine();
        }
    }

}


