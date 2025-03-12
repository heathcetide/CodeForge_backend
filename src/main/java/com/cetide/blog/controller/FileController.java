package com.cetide.blog.controller;

import com.cetide.blog.config.FileStorageContext;
import com.cetide.blog.model.enums.StorageType;
import com.cetide.blog.service.FileStorageService;
import com.cetide.blog.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@Tag(name = "文件存储模块")
public class FileController {

    @Autowired
    private FileStorageContext fileStorageContext;

    /**
     * 上传文件
     * @param file 文件
     * @param storageType 存储类型
     * @param async 是否异步
     * @return 文件url
     * @throws IOException IO异常
     */
    @Operation(summary = "上传文件", description = "支持存储类型：LOCAL, OSS, COS, QINIU；支持同步和异步上传")
    @PostMapping("/upload")
    public ApiResponse<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("storageType") StorageType storageType,
            @RequestParam(value = "async", defaultValue = "false") boolean async) throws IOException {
        FileStorageService storageService = fileStorageContext.getStorageService(storageType);
        String fileUrl;
        if (async) {
            storageService.uploadFileAsync(file);
            fileUrl = "文件正在异步上传中";
        } else {
            fileUrl = storageService.uploadFile(file);
        }
        return ApiResponse.success(fileUrl);
    }

    /**
     * 下载文件
     * @param filePath 文件路径
     * @param storageType 存储类型
     * @return 字节码
     */
    @Operation(summary = "下载文件")
    @GetMapping("/download")
    public ApiResponse<byte[]> downloadFile(
            @RequestParam("filePath") String filePath,
            @RequestParam("storageType") StorageType storageType) {
        FileStorageService storageService = fileStorageContext.getStorageService(storageType);
        byte[] data = storageService.downloadFile(filePath);
        return ApiResponse.success(data);
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @param storageType 存储类型
     * @return 返回是否成功
     */
    @Operation(summary = "删除文件")
    @DeleteMapping("/delete")
    public ApiResponse<String> deleteFile(
            @RequestParam("filePath") String filePath,
            @RequestParam("storageType") StorageType storageType) {
        FileStorageService storageService = fileStorageContext.getStorageService(storageType);
        storageService.deleteFile(filePath);
        return ApiResponse.success("文件删除成功");
    }
}
