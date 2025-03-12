package com.cetide.blog.config;

import com.cetide.blog.common.chain.storage.ChainOfResponsibilityFileStorageDecorator;
import com.cetide.blog.common.chain.storage.FileProcessorChain;
import com.cetide.blog.model.enums.StorageType;
import com.cetide.blog.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileStorageContext {

    private final Map<String, FileStorageService> storageServiceMap;
    private final FileProcessorChain fileProcessorChain;

    @Autowired
    public FileStorageContext(Map<String, FileStorageService> storageServiceMap,
                              FileProcessorChain fileProcessorChain) {
        this.storageServiceMap = storageServiceMap;
        this.fileProcessorChain = fileProcessorChain;
    }

    public FileStorageService getStorageService(StorageType type) {
        FileStorageService service;
        switch (type) {
            case LOCAL:
                service = storageServiceMap.get("localFileStorageService");
                break;
            case OSS:
                service = storageServiceMap.get("ossFileStorageService");
                break;
            case COS:
                service = storageServiceMap.get("cosFileStorageService");
                break;
            case QINIU:
                service = storageServiceMap.get("qiniuStorageService");
                break;
            default:
                throw new IllegalArgumentException("不支持的存储类型: " + type);
        }
        return new ChainOfResponsibilityFileStorageDecorator(service, fileProcessorChain);
    }
}
