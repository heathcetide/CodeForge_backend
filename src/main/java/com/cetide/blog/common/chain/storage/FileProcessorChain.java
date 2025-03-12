package com.cetide.blog.common.chain.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileProcessorChain {

    private FileProcessor chainHead;

    public FileProcessorChain() {
        // 构造责任链：校验 -> 加密
        FileProcessor validationProcessor = new FileValidationProcessor();
        FileProcessor encryptionProcessor = new FileEncryptionProcessor();

        validationProcessor.setNext(encryptionProcessor);
        this.chainHead = validationProcessor;
    }

    /**
     * 对文件执行责任链处理
     */
    public MultipartFile process(MultipartFile file) {
        return chainHead.process(file);
    }
}
