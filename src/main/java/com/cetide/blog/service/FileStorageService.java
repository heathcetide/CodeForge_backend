package com.cetide.blog.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    /**
     * 同步上传文件，返回文件存储后地址或标识
     */
    String uploadFile(MultipartFile file) throws IOException;

    /**
     * 异步上传文件（例如利用线程或异步队列处理）
     */
    void uploadFileAsync(MultipartFile file);

    /**
     * 下载文件，返回文件字节流
     */
    byte[] downloadFile(String filePath);

    /**
     * 删除文件
     */
    void deleteFile(String filePath);
}
