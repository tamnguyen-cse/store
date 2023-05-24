package com.demo.store.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

    /**
     * Upload a file as byte array to S3.
     *
     * @param data the file data
     * @param name the file name
     * @param path the file path
     * @return file path
     */
    public String uploadFileSync(byte[] data, String name, String path, String contentType);

    /**
     * Upload a file as byte array to S3.
     *
     * @param data the file data
     * @param name the file name
     * @param path the file path
     * @return file path
     */
    public String uploadFileAsync(byte[] data, String name, String path, String contentType);

    /**
     * Upload a multi part file to S3.
     *
     * @param multipartFile the file data
     * @param path          the file path
     * @return file path
     */
    public String uploadFileSync(MultipartFile multipartFile, String path);

    /**
     * Upload a multi part file to S3.
     *
     * @param multipartFile the file data
     * @param path          the file path
     * @return file path
     */
    public String uploadFileAsync(MultipartFile multipartFile, String path);

    /**
     * Upload files from S3.
     *
     * @param multipartFiles the files data
     * @param path           the files path
     * @return list of file paths
     */
    public List<String> uploadFiles(MultipartFile[] multipartFiles, String path);

    /**
     * Download a file from S3.
     *
     * @param fileName the file name
     * @return presigned URL
     */
    public String downloadFile(String fileName, String path);

    public String downloadFile(String fileName, String path, String privateBucket);

    /**
     * Download files from S3.
     *
     * @param fileNames the list of file name
     * @return presigned URLs
     */
    public List<String> downloadFiles(List<String> fileNames, String path);

    /**
     * Delete a file from S3.
     *
     * @param path the file path
     */
    public int deleteFile(String path);

    /**
     * Get presigned URL of a file from S3.
     *
     * @param path the file path
     * @return the presigned URL
     */
    public String getPresignedUrl(String path);

    /**
     * Get presigned URL from a specific S3 bucket of a file from S3.
     *
     * @param path          the file path
     * @param privateBucket the S3 bucket name
     * @return the presigned URL
     */
    public String getPresignedUrl(String path, String privateBucket);

}
