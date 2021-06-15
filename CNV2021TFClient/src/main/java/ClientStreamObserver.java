import cnv2021tfservice.ImageResult;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class ClientStreamObserver implements StreamObserver<ImageResult> {

    boolean isCompleted, success;
    List<ImageResult> results = new ArrayList<>();

    @Override
    public void onNext(ImageResult result) {
        results.add(result);
    }

    @Override
    public void onError(Throwable throwable) {
        isCompleted = true; success = false;
    }

    @Override
    public void onCompleted() {
        isCompleted = true; success = true;
    }
}
