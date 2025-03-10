package com.cetide.blog.util.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cetide.blog.common.constants.Constants;
import com.cetide.blog.common.exception.ServiceException;
import com.cetide.blog.common.exception.file.InvalidExtensionException;
import com.cetide.blog.util.DateUtils;
import com.cetide.blog.util.StringUtils;
import com.cetide.blog.util.uuid.Seq;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

/**
 * 文件上传工具类
 *
 * @author ruoyi
 */
public class FileUploadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024L;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir;

    static {
        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            defaultBaseDir = "D:/bonss_live/uploadPath";
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            defaultBaseDir = "/home/bonss_live/uploadPath";
        }
    }

    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String upload(MultipartFile file) {
        try {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            log.error("文件上传失败,原因{}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param path 上传的文件路径
     * @return 文件名称
     * @throws Exception
     */
    public static final String uploadByPath(String path) {
        try {
            File file = new File(path);
            String fileName = StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
                    FilenameUtils.getBaseName(IdUtil.simpleUUID()), Seq.getId(Seq.uploadSeqType), FileUtil.getSuffix(file));

            String absPath = getAbsoluteFile(getDefaultBaseDir(), fileName).getAbsolutePath();
            byte[] fileData = FileUtil.readBytes(file);
            try (FileOutputStream fos = new FileOutputStream(absPath)) {
                fos.write(fileData);
            }
            return getPathFileName(fileName);
        } catch (Exception e) {
            log.error("文件上传失败,原因{}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            log.error("文件上传失败,原因{}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws IOException               比如读写文件出错时
     * @throws ServiceException 文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension) {
        try {
            assertAllowed(file, allowedExtension);

            String fileName = extractFilename(file);

            String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
            file.transferTo(Paths.get(absPath));
            return getPathFileName(fileName);
        } catch (Exception e) {
            log.error("文件上传失败,原因{}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
                FilenameUtils.getBaseName(IdUtil.simpleUUID()), Seq.getId(Seq.uploadSeqType), getExtension(file));
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String fileName) throws IOException {
        return Constants.RESOURCE_PREFIX + "/" + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws InvalidExtensionException {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new ServiceException("文件大小不能超过" + DEFAULT_MAX_SIZE / 1024 / 1024 + "Mb");
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    public static void deleteFile(String url) {
        String path = StrUtil.replaceFirst(url, Constants.RESOURCE_PREFIX, FileUploadUtils.getDefaultBaseDir());
        FileUtils.deleteFile(path);
    }

    public static String uploadImgBase64(String imgBase64) {
        try {
            String fileName =StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
                    FilenameUtils.getBaseName(IdUtil.simpleUUID()), Seq.getId(Seq.uploadSeqType), "png");
            String absPath = getAbsoluteFile(getDefaultBaseDir(), fileName).getAbsolutePath();
            String[] parts = imgBase64.split(",");
            byte[] fileData = Base64.getDecoder().decode(parts[1]);
            try (FileOutputStream fos = new FileOutputStream(absPath)) {
                fos.write(fileData);
            }
            return getPathFileName(fileName);
        } catch (Exception e) {
            log.error("文件上传失败,原因{}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
