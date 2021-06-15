package cnv2021tfservice.services;

import cnv2021tfservice.ImageRequest;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.nio.ByteBuffer;

public class CloudStorageService {
    public static Storage storage = null;
    public WriteChannel writer;
    private BlobId blobId;

    public static void init(GoogleCredentials credentials) {
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        storage = storageOptions.getService();
    }

    public void uploadToStorage(ImageRequest request) throws IOException {
        if (writer == null) {
            String blobName = request.getBlobName();
            String contentType = request.getContentType();
            blobId = BlobId.of("g12-t1-v2021-tf-eu", blobName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
            writer = storage.writer(blobInfo);
        }
        byte[] buffer = request.getImageBlockBytes().toByteArray();
        writer.write(ByteBuffer.wrap(buffer));
    }

    public BlobId closeWriter() throws IOException {
        if (writer != null) writer.close();
        return blobId;
    }

}
