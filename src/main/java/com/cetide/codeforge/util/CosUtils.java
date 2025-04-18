package com.cetide.codeforge.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CosUtils {

    static String tmpSecretId = "AKIDEI7f5KMAkCQPAi27xd5JJ4clhOq31kET";
    static String tmpSecretKey = "1nM6dx3vb3HG0WoshIemgLmzyjGZeU6U";
    static String sessionToken = "TOKEN";
    static BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
    // 2 设置 bucket 的地域
    // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
    static Region region = new Region("ap-chengdu"); //COS_REGION 参数：配置成存储桶 bucket 的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
    static ClientConfig clientConfig = new ClientConfig(region);
    // 3 生成 cos 客户端
    static COSClient cosClient = new COSClient(cred, clientConfig);


    public static String uploadFile(File file, String fileName) throws CosClientException, IOException {
        // 指定文件将要存放的存储桶
        String bucketName = "cetide-1325039295";

        // 创建 PutObjectRequest 对象
        FileInputStream inputStream = new FileInputStream(file);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, null);

        // 执行上传操作
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        // 获取文件的 URL
        URL objectUrl = cosClient.getObjectUrl(bucketName, fileName);

        // 关闭输入流
        inputStream.close();

        return objectUrl.toString();
    }

    public static String uploadFileByCos(MultipartFile file, String fileName) throws CosClientException, IOException {
        // 指定文件将要存放的存储桶
        String bucketName = "cetide-1325039295";

        // 创建 PutObjectRequest 对象
        InputStream inputStream = file.getInputStream();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, null);

        // 执行上传操作
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        // 获取文件的 URL
        URL objectUrl = cosClient.getObjectUrl(bucketName, fileName);

        return objectUrl.toString();
    }

//    public static String uploadFile(String localFilePath, String fileName) {
//        // 指定要上传的文件
//        File localFile = new File(localFilePath);
//        // 指定文件将要存放的存储桶
//        String bucketName = "cetide-1325039295";
//        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, localFile);
//        cosClient.putObject(putObjectRequest);
//        URL objectUrl = cosClient.getObjectUrl(bucketName, fileName);
//        // 关闭客户端(关闭后台线程)
//        cosClient.shutdown();
//        return objectUrl.toString();
//    }

    public static boolean deleteFile(String fileName) {
        try {
            // 指定要删除的存储桶名称和文件
            String bucketName = "cetide-1325039295";
            // 指定要删除的存储桶名称和文件
            cosClient.deleteObject(bucketName, fileName);
            // 关闭客户端(关闭后台线程)
//            cosClient.shutdown();
            return true;
        } catch (CosClientException cce) {
            cce.printStackTrace();
            return false;
        }
    }
}
