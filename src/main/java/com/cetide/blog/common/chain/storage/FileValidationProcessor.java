package com.cetide.blog.common.chain.storage;

import org.springframework.web.multipart.MultipartFile;

public class FileValidationProcessor implements FileProcessor {

    private FileProcessor next;

    @Override
    public MultipartFile process(MultipartFile file) {
        // 示例：简单校验文件名是否为空
        System.out.println("校验文件：" + file.getOriginalFilename());
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            throw new IllegalArgumentException("文件名不能为空");
        }
        // 校验通过，交由下一个处理器处理
        if (next != null) {
            return next.process(file);
        }
        return file;
    }

    @Override
    public void setNext(FileProcessor next) {
        this.next = next;
    }
}
