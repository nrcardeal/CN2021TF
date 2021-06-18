package cnv2021tfservice.observers;

import cnv2021tfservice.ImageRequest;
import cnv2021tfservice.ImageResult;
import cnv2021tfservice.services.CloudPubSubService;
import cnv2021tfservice.services.CloudStorageService;
import cnv2021tfservice.services.DetectTranslateService;
import cnv2021tfservice.services.FirestoreService;
import com.google.cloud.storage.BlobId;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class ServerObserver implements StreamObserver<ImageRequest> {

    private final StreamObserver<ImageResult> imageObserver;
    private final CloudStorageService cloudStorageService = new CloudStorageService();
    private final CloudPubSubService cloudPubSubService = new CloudPubSubService();
    private final DetectTranslateService detectTranslateService = new DetectTranslateService();

    public ServerObserver(StreamObserver<ImageResult> imageObserver) {
        this.imageObserver = imageObserver;
    }


    @Override
    public void onNext(ImageRequest imageRequest) {
        try {
            cloudStorageService.uploadToStorage(imageRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        imageObserver.onError(throwable.getCause());
    }

    @Override
    public void onCompleted() {
        try {
            BlobId blobId = cloudStorageService.closeWriter();
            ImageResult result = ImageResult
                    .newBuilder()
                    .setId(blobId.getBucket() + '-' + blobId.getName())
                    .build();
            imageObserver.onNext(result);
            imageObserver.onCompleted();
            cloudPubSubService.publishMessage(
                    result.getId() + " " + blobId.getBucket() + " " + blobId.getName(),
                    blobId.getName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
