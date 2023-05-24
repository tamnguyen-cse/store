package com.demo.store.service.impl;

import com.demo.store.service.AWSS3Service;
import com.demo.store.utils.StringUtils;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Slf4j
@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    /**
     * The Access key ID.
     */
    @Value("${aws.s3.credentials.access-key-id}")
    private String accessKeyId;

    /**
     * The Secret access key.
     */
    @Value("${aws.s3.credentials.secret-access-key}")
    private String secretAccessKey;

    /**
     * The S3 bucket name.
     */
    @Value("${aws.s3.bucket}")
    private String bucket;

    /**
     * The S3 region.
     */
    @Value("${aws.s3.region}")
    private String region;

    /**
     * The presigned URL duration.
     */
    @Value("${aws.s3.presigned-url.duration}")
    private Long presignedUrlDuration;

    private S3Client client = null;
    private S3Presigner presigner = null;

    @PostConstruct
    private void initializeAmazon() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        client = S3Client.builder().region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials)).build();
        presigner = S3Presigner.builder().region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials)).build();
    }

    @Override
    public String uploadFileSync(byte[] data, String name, String path, String contentType) {
        if (data == null) {
            return null;
        }

        if (StringUtils.isEmpty(contentType)) {
            contentType = MediaType.MULTIPART_FORM_DATA_VALUE.toString();
        }
        String filePath = path + name;
        log.debug("IN - file path: {}, ", filePath);
        PutObjectRequest request = PutObjectRequest.builder().bucket(bucket).key(filePath)
                .contentType(contentType).build();
        PutObjectResponse response;
        try {
            response = client.putObject(request, RequestBody.fromBytes(data));
            log.debug("AWS response: {}", response.sdkHttpResponse().statusCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.debug("OUT - result {}", filePath);
        return filePath;
    }

    @Override
    public String uploadFileAsync(byte[] data, String name, String path, String contentType) {
        if (data == null) {
            return null;
        }

        String filePath = path + name;
        CompletableFuture.runAsync(() -> {
            uploadFileSync(data, name, path, contentType);
        });
        return filePath;
    }

    @Override
    public String uploadFileSync(MultipartFile file, String path) {
        if (file == null) {
            return null;
        }

        try {
            String name = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] data = file.getBytes();
            return this.uploadFileSync(data, name, path, contentType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadFileAsync(MultipartFile file, String path) {
        if (file == null) {
            return null;
        }

        String filePath = path + file.getOriginalFilename();
        CompletableFuture.runAsync(() -> {
            uploadFileSync(file, path);
        });
        return filePath;
    }

    @Override
    public List<String> uploadFiles(MultipartFile[] files, String path) {

        List<String> result = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = uploadFileAsync(file, path);
            if (StringUtils.isNotEmpty(url)) {
                result.add(url);
            }
        }

        return result;
    }

    @Override
    public String downloadFile(String fileName, String path) {
        return getPresignedUrl(path + fileName);
    }

    @Override
    public String downloadFile(String fileName, String path, String privateBucket) {
        return getPresignedUrl(path + fileName, privateBucket);
    }

    @Override
    public List<String> downloadFiles(List<String> fileNames, String path) {
        List<String> urls = new ArrayList<>();
        for (String fileName : fileNames) {
            urls.add(downloadFile(fileName, path));
        }
        return urls;
    }

    @Override
    public int deleteFile(String path) {
        if (StringUtils.isEmpty(path)) {
            return 0;
        }

        String fileName = path.substring(path.lastIndexOf("/") + 1);
        log.debug("IN - image name: {}, ", fileName);

        DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucket).key(path)
                .build();
        DeleteObjectResponse response = client.deleteObject(request);
        int statusCode = response.sdkHttpResponse().statusCode();

        log.debug("OUT - result {}", statusCode);
        return statusCode;
    }

    @Override
    public String getPresignedUrl(String path) {
        return getPresignedUrl(path, this.bucket);
    }

    @Override
    public String getPresignedUrl(String path, String privateBucket) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }

        String fileName = path.substring(path.lastIndexOf("/") + 1);
        log.debug("IN - image name: {}, ", fileName);

        // Create get presigned URL request
        GetObjectRequest request = GetObjectRequest.builder().bucket(privateBucket).key(path)
                .build();
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(presignedUrlDuration.longValue()))
                .getObjectRequest(request).build();

        // Generate the presigned URL
        PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(
                getObjectPresignRequest);
        String url = presignedRequest.url().toString();

        log.debug("OUT - result {}", url);
        return url;
    }

}
