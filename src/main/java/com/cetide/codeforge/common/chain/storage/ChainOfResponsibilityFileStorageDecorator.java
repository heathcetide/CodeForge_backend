package com.cetide.codeforge.common.chain.storage;

import com.cetide.codeforge.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 装饰器：在文件上传前先经过责任链处理
 */
public class ChainOfResponsibilityFileStorageDecorator implements FileStorageService {

    private final FileStorageService delegate;
    private final FileProcessorChain processorChain;

    public ChainOfResponsibilityFileStorageDecorator(FileStorageService delegate, FileProcessorChain processorChain) {
        this.delegate = delegate;
        this.processorChain = processorChain;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        // 通过责任链对文件进行处理后再上传
        MultipartFile processedFile = processorChain.process(file);
        return delegate.uploadFile(processedFile);
    }

    @Override
    public void uploadFileAsync(MultipartFile file) {
        MultipartFile processedFile = processorChain.process(file);
        delegate.uploadFileAsync(processedFile);
    }

    @Override
    public byte[] downloadFile(String filePath) {
        return delegate.downloadFile(filePath);
    }

    @Override
    public void deleteFile(String filePath) {
        delegate.deleteFile(filePath);
    }
}
