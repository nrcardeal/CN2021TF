package cnv2021tfservice.observers;

import cnv2021tfservice.ImageRequest;
import cnv2021tfservice.ImageResult;
import cnv2021tfservice.exceptions.BlobAlreadyExistsException;
import cnv2021tfservice.services.CloudPubSubService;
import cnv2021tfservice.services.CloudStorageService;
import com.google.cloud.compute.v1.Instance;
import com.google.cloud.storage.BlobId;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.net.InetAddress;

public class ServerObserver implements StreamObserver<ImageRequest> {

    private final StreamObserver<ImageResult> imageObserver;
    private final CloudStorageService cloudStorageService = new CloudStorageService();
    private final CloudPubSubService cloudPubSubService = new CloudPubSubService();
    private boolean error = false;

    public ServerObserver(StreamObserver<ImageResult> imageObserver) {
        this.imageObserver = imageObserver;
    }


    @Override
    public void onNext(ImageRequest imageRequest) {
        if(error) return;
        try {
            cloudStorageService.uploadToStorage(imageRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BlobAlreadyExistsException e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        error = true;
        imageObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        try {
            BlobId blobId = cloudStorageService.closeWriter();
            if(error) return;
            ImageResult result = ImageResult
                    .newBuilder()
                    .setId(blobId.getBucket() + '-' + blobId.getName())
                    .build();
            imageObserver.onNext(result);
            imageObserver.onCompleted();
            cloudPubSubService.publishMessage(
                    result.getId() + " " +
                            blobId.getBucket() + " " +
                            blobId.getName() + " " +
                            InetAddress.getLocalHost().getHostAddress(),
                    blobId.getName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
