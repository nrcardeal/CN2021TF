package cnv2021tfservice;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.1)",
    comments = "Source: CNV2021TFService.proto")
public final class ServiceGrpc {

  private ServiceGrpc() {}

  public static final String SERVICE_NAME = "cnv2021tfservice.Service";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cnv2021tfservice.ImageRequest,
      cnv2021tfservice.ImageResult> getUploadImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadImage",
      requestType = cnv2021tfservice.ImageRequest.class,
      responseType = cnv2021tfservice.ImageResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<cnv2021tfservice.ImageRequest,
      cnv2021tfservice.ImageResult> getUploadImageMethod() {
    io.grpc.MethodDescriptor<cnv2021tfservice.ImageRequest, cnv2021tfservice.ImageResult> getUploadImageMethod;
    if ((getUploadImageMethod = ServiceGrpc.getUploadImageMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getUploadImageMethod = ServiceGrpc.getUploadImageMethod) == null) {
          ServiceGrpc.getUploadImageMethod = getUploadImageMethod =
              io.grpc.MethodDescriptor.<cnv2021tfservice.ImageRequest, cnv2021tfservice.ImageResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cnv2021tfservice.ImageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cnv2021tfservice.ImageResult.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("UploadImage"))
              .build();
        }
      }
    }
    return getUploadImageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cnv2021tfservice.ImageResult,
      cnv2021tfservice.Labels> getGetLabelsListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLabelsList",
      requestType = cnv2021tfservice.ImageResult.class,
      responseType = cnv2021tfservice.Labels.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cnv2021tfservice.ImageResult,
      cnv2021tfservice.Labels> getGetLabelsListMethod() {
    io.grpc.MethodDescriptor<cnv2021tfservice.ImageResult, cnv2021tfservice.Labels> getGetLabelsListMethod;
    if ((getGetLabelsListMethod = ServiceGrpc.getGetLabelsListMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getGetLabelsListMethod = ServiceGrpc.getGetLabelsListMethod) == null) {
          ServiceGrpc.getGetLabelsListMethod = getGetLabelsListMethod =
              io.grpc.MethodDescriptor.<cnv2021tfservice.ImageResult, cnv2021tfservice.Labels>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLabelsList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cnv2021tfservice.ImageResult.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cnv2021tfservice.Labels.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("GetLabelsList"))
              .build();
        }
      }
    }
    return getGetLabelsListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cnv2021tfservice.FilterRequest,
      cnv2021tfservice.FilterResult> getFilterFilesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FilterFiles",
      requestType = cnv2021tfservice.FilterRequest.class,
      responseType = cnv2021tfservice.FilterResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cnv2021tfservice.FilterRequest,
      cnv2021tfservice.FilterResult> getFilterFilesMethod() {
    io.grpc.MethodDescriptor<cnv2021tfservice.FilterRequest, cnv2021tfservice.FilterResult> getFilterFilesMethod;
    if ((getFilterFilesMethod = ServiceGrpc.getFilterFilesMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFilterFilesMethod = ServiceGrpc.getFilterFilesMethod) == null) {
          ServiceGrpc.getFilterFilesMethod = getFilterFilesMethod =
              io.grpc.MethodDescriptor.<cnv2021tfservice.FilterRequest, cnv2021tfservice.FilterResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FilterFiles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cnv2021tfservice.FilterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cnv2021tfservice.FilterResult.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("FilterFiles"))
              .build();
        }
      }
    }
    return getFilterFilesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceStub>() {
        @java.lang.Override
        public ServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceStub(channel, callOptions);
        }
      };
    return ServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceBlockingStub>() {
        @java.lang.Override
        public ServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceBlockingStub(channel, callOptions);
        }
      };
    return ServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceFutureStub>() {
        @java.lang.Override
        public ServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceFutureStub(channel, callOptions);
        }
      };
    return ServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<cnv2021tfservice.ImageRequest> uploadImage(
        io.grpc.stub.StreamObserver<cnv2021tfservice.ImageResult> responseObserver) {
      return asyncUnimplementedStreamingCall(getUploadImageMethod(), responseObserver);
    }

    /**
     */
    public void getLabelsList(cnv2021tfservice.ImageResult request,
        io.grpc.stub.StreamObserver<cnv2021tfservice.Labels> responseObserver) {
      asyncUnimplementedUnaryCall(getGetLabelsListMethod(), responseObserver);
    }

    /**
     */
    public void filterFiles(cnv2021tfservice.FilterRequest request,
        io.grpc.stub.StreamObserver<cnv2021tfservice.FilterResult> responseObserver) {
      asyncUnimplementedUnaryCall(getFilterFilesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadImageMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                cnv2021tfservice.ImageRequest,
                cnv2021tfservice.ImageResult>(
                  this, METHODID_UPLOAD_IMAGE)))
          .addMethod(
            getGetLabelsListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                cnv2021tfservice.ImageResult,
                cnv2021tfservice.Labels>(
                  this, METHODID_GET_LABELS_LIST)))
          .addMethod(
            getFilterFilesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                cnv2021tfservice.FilterRequest,
                cnv2021tfservice.FilterResult>(
                  this, METHODID_FILTER_FILES)))
          .build();
    }
  }

  /**
   */
  public static final class ServiceStub extends io.grpc.stub.AbstractAsyncStub<ServiceStub> {
    private ServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<cnv2021tfservice.ImageRequest> uploadImage(
        io.grpc.stub.StreamObserver<cnv2021tfservice.ImageResult> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getUploadImageMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void getLabelsList(cnv2021tfservice.ImageResult request,
        io.grpc.stub.StreamObserver<cnv2021tfservice.Labels> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLabelsListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void filterFiles(cnv2021tfservice.FilterRequest request,
        io.grpc.stub.StreamObserver<cnv2021tfservice.FilterResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFilterFilesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServiceBlockingStub> {
    private ServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public cnv2021tfservice.Labels getLabelsList(cnv2021tfservice.ImageResult request) {
      return blockingUnaryCall(
          getChannel(), getGetLabelsListMethod(), getCallOptions(), request);
    }

    /**
     */
    public cnv2021tfservice.FilterResult filterFiles(cnv2021tfservice.FilterRequest request) {
      return blockingUnaryCall(
          getChannel(), getFilterFilesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ServiceFutureStub> {
    private ServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cnv2021tfservice.Labels> getLabelsList(
        cnv2021tfservice.ImageResult request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLabelsListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cnv2021tfservice.FilterResult> filterFiles(
        cnv2021tfservice.FilterRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getFilterFilesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_LABELS_LIST = 0;
  private static final int METHODID_FILTER_FILES = 1;
  private static final int METHODID_UPLOAD_IMAGE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_LABELS_LIST:
          serviceImpl.getLabelsList((cnv2021tfservice.ImageResult) request,
              (io.grpc.stub.StreamObserver<cnv2021tfservice.Labels>) responseObserver);
          break;
        case METHODID_FILTER_FILES:
          serviceImpl.filterFiles((cnv2021tfservice.FilterRequest) request,
              (io.grpc.stub.StreamObserver<cnv2021tfservice.FilterResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_IMAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadImage(
              (io.grpc.stub.StreamObserver<cnv2021tfservice.ImageResult>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cnv2021tfservice.CNV2021TFService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Service");
    }
  }

  private static final class ServiceFileDescriptorSupplier
      extends ServiceBaseDescriptorSupplier {
    ServiceFileDescriptorSupplier() {}
  }

  private static final class ServiceMethodDescriptorSupplier
      extends ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServiceFileDescriptorSupplier())
              .addMethod(getUploadImageMethod())
              .addMethod(getGetLabelsListMethod())
              .addMethod(getFilterFilesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
