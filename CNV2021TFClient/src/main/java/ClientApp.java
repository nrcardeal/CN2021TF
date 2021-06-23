import cnv2021tfservice.*;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class ClientApp {

    //to use on local machine
//    private static String svcIP = "localhost";
    //to use with vm
    private static String svcIP = "";
    private static int svcPort = 8000;
    private static ManagedChannel channel;
    private static ServiceGrpc.ServiceStub noBlockStub;
    private static ServiceGrpc.ServiceBlockingStub blockingStub;

    public static void main(String[] args) {
        try {
            if(!svcIP.equals("localhost")) {
                String cfURL = "https://europe-west3-g12-t1d-v2021.cloudfunctions.net/cn-http-function?name=cnv2021tf-server-instance-group";
                HttpClient client = HttpClient.newBuilder().build();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(cfURL))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200)
                    svcIP = randomizeIP(response.body());
            }
            channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                    // Channels are secure by default (via SSL/TLS).
                    // For the example we disable TLS to avoid needing certificates.
                    .usePlaintext()
                    .build();
            noBlockStub = ServiceGrpc.newStub(channel);
            blockingStub = ServiceGrpc.newBlockingStub(channel);
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
        if (Files.notExists(uploadFrom)) {
            System.out.println("This File does not Exist!");
        }
        else{
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
                while (!streamObserver.isCompleted) {
                    System.out.println("Uploading Image...");
                    Thread.sleep(1000);
                }
                if(streamObserver.success)
//                    System.out.println("File with that name already exists. Please insert a file with a different name.");
//                else
                System.out.println("Image uploaded with ID: " + streamObserver.results.get(0).getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void ListLabels() {
        System.out.println("Please insert file Id:");
        Scanner in = new Scanner(System.in);
        String fileId = in.nextLine();
        ImageResult imageResult = ImageResult.newBuilder()
                .setId(fileId)
                .build();
        Labels labels = blockingStub.getLabelsList(imageResult);
        System.out.println("Labels:");
        for (String label: labels.getLabelsList()) {
            System.out.println(label);
        }
        System.out.println();
        System.out.println("Características:");
        for (String label: labels.getLabelsTranslationsList()) {
            System.out.println(label);
        }


    }

    static void FilterFiles() {
        System.out.println("Filter's initial date? (dd/MM/yyyy)");
        Scanner in = new Scanner(System.in);
        String initDate = in.nextLine();
        System.out.println("Filter's end date? (dd/MM/yyyy)");
        in = new Scanner(System.in);
        String endDate = in.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:m");
        try {
            if(!sdf.parse(initDate).before(sdf.parse(endDate)))
                System.out.println("The End Date is before the Initial Date!");
            else {
                System.out.println("Filter's label");

                in = new Scanner(System.in);
                String label = in.nextLine();
                FilterRequest request = FilterRequest
                        .newBuilder()
                        .setInitDate(initDate)
                        .setEndDate(endDate)
                        .setLabel(label)
                        .build();
                FilterResult result = blockingStub.filterFiles(request);
                System.out.println("Searching for files...");
                if (!result.getFilenameList().isEmpty()) {
                    System.out.println("Filtered Files:");
                    for (String name : result.getFilenameList()) {
                        System.out.println(name);
                    }
                } else {
                    System.out.println("There were no files with those filters.");
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid date Format!");
        }
    }

    static String randomizeIP(String body){
        Gson gson = new Gson();
        List<String> ips = gson.fromJson(body, List.class);
        int idx = (int )(Math.random() *(ips.size()-1 ));
        return ips.get(idx);
    }
}


