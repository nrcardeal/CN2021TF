import cnv2021tfservice.FilterResult
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class FilterStreamObserver implements StreamObserver<FilterResult> {

    boolean isCompleted, success;
    FilterResult result;

    @Override
    public void onNext(FilterResult result) {
        this.result = result
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