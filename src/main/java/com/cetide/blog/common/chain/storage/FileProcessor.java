package com.cetide.blog.common.chain.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理器接口：用于对文件进行预处理，例如校验、加密、压缩等。
 */
public interface FileProcessor {
    /**
     * 处理文件，返回处理后的文件
     */
    MultipartFile process(MultipartFile file);

    /**
     * 设置下一个处理器
     */
    void setNext(FileProcessor next);
}