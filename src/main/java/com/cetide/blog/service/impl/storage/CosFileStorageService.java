package com.cetide.blog.service.impl.storage;

import cn.hutool.core.lang.UUID;
import com.cetide.blog.service.FileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.cetide.blog.util.CosUtils.uploadFile;
import static com.cetide.blog.util.CosUtils.uploadFileByCos;

@Service("cosFileStorageService")
public class CosFileStorageService implements FileStorageService {

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        // 检查上传的文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }

        // 校验文件类型，可以根据需要添加文件类型限制
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename).toLowerCase();
        if (!isValidFileExtension(extension)) {
            throw new IllegalArgumentException("不支持的文件类型");
        }

        // 生成唯一的文件名
        String fileName = UUID.randomUUID() + "." + extension;
        return uploadFileByCos(file, fileName);
    }

    @Override
    public void uploadFileAsync(MultipartFile file) {
        new Thread(() -> {
            try {
                uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public byte[] downloadFile(String filePath) {
        System.out.println("从 COS 下载文件: " + filePath);
        return new byte[0]; // 示例返回
    }

    @Override
    public void deleteFile(String filePath) {
        System.out.println("从 COS 删除文件: " + filePath);
    }

    // 检查文件扩展名是否符合要求
    private boolean isValidFileExtension(String extension) {
        // 允许的文件类型（例如图片类型）
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
    }
}
