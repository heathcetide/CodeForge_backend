package com.cetide.codeforge.service.impl.storage;

import com.cetide.codeforge.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service("localFileStorageService")
public class LocalFileStorageService implements FileStorageService {

    private final String localDir = "/tmp/uploads/";

    public LocalFileStorageService() {
        try {
            Files.createDirectories(Paths.get(localDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String filePath = localDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            File dest = new File(filePath);
            file.transferTo(dest);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("本地文件上传失败");
        }
    }

    @Override
    public void uploadFileAsync(MultipartFile file) {
        // 演示：简单地开启一个新线程进行异步上传
        new Thread(() -> uploadFile(file)).start();
    }

    @Override
    public byte[] downloadFile(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("本地文件下载失败");
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("本地文件删除失败");
        }
    }
}
