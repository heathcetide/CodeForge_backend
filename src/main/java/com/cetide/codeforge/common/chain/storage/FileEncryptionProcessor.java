package com.cetide.codeforge.common.chain.storage;

import org.springframework.web.multipart.MultipartFile;

public class FileEncryptionProcessor implements FileProcessor {

    private FileProcessor next;

    @Override
    public MultipartFile process(MultipartFile file) {
        // 示例：伪装加密处理，实际业务中应对文件内容进行加密
        System.out.println("对文件进行加密：" + file.getOriginalFilename());
        // 此处可以返回一个加密后的 MultipartFile 对象
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
