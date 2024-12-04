package ait.cohort46.images;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    private final String bucketName = "pets-care-images"; // Название бакета

    public String uploadFile(MultipartFile file, String folder) {
        String fileName = folder + "/" + file.getOriginalFilename();
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }

    public URL getFileUrl(String fileName) {
        S3Presigner presigner = S3Presigner.create();
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(objectRequest)
                .signatureDuration(Duration.ofMinutes(60)) // Время действия ссылки
                .build();
        return presigner.presignGetObject(presignRequest).url();
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(b -> b.bucket(bucketName).key(fileName));
    }
}
