import cnv2021tfservice.Labels
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class LabelsStreamObserver implements StreamObserver<Labels> {

    boolean isCompleted, success;
    Labels labels;

    @Override
    public void onNext(Labels labels) {
        this.labels=labels;
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
