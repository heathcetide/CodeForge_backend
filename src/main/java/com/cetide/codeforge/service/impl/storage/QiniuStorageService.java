package com.cetide.codeforge.service.impl.storage;

import com.cetide.codeforge.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("qiniuStorageService")
public class QiniuStorageService implements FileStorageService {

    @Override
    public String uploadFile(MultipartFile file) {
        String fileUrl = "https://upload.qiniu.com/" + file.getOriginalFilename();
        System.out.println("上传文件到七牛云: " + fileUrl);
        return fileUrl;
    }

    @Override
    public void uploadFileAsync(MultipartFile file) {
        new Thread(() -> uploadFile(file)).start();
    }

    @Override
    public byte[] downloadFile(String filePath) {
        System.out.println("从七牛云下载文件: " + filePath);
        return new byte[0]; // 示例返回
    }

    @Override
    public void deleteFile(String filePath) {
        System.out.println("从七牛云删除文件: " + filePath);
    }
}
