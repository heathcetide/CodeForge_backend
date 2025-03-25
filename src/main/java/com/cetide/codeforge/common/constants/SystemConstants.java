package com.cetide.codeforge.common.constants;

/**
 * 系统常量
 *
 * @author heathcetide
 */
public interface SystemConstants {


    // ----------------- HTTP 状态码 -----------------

    // HTTP 状态码：请求成功
    int CODE_SUCCESS = 200; // 200：操作成功

    // 资源创建成功
    int CODE_CREATED = 201; // 201：创建成功

    // 请求已接受，但处理尚未完成
    int CODE_ACCEPTED = 202; // 202：请求已接受

    // 请求成功但无返回内容
    int CODE_NO_CONTENT = 204; // 204：无内容

    // HTTP 状态码：请求错误（客户端错误，例如参数错误）
    int CODE_BAD_REQUEST = 400; // 400：请求错误

    // HTTP 状态码：未授权（缺少或无效的身份验证凭据）
    int CODE_UNAUTHORIZED = 401; // 401：未授权

    // HTTP 状态码：禁止访问（服务器理解请求但拒绝执行）
    int CODE_FORBIDDEN = 403; // 403：禁止访问

    // HTTP 状态码：未找到（请求的资源不存在）
    int CODE_NOT_FOUND = 404; // 404：未找到

    // HTTP 状态码：服务器内部错误（服务器在处理请求时发生异常）
    int CODE_SERVER_ERROR = 500; // 500：服务器内部错误

    // 服务不可用
    int CODE_SERVICE_UNAVAILABLE = 503; // 503：服务不可用

    // ----------------- 文件或路径相关常量 -----------------

    // 默认上传目录
    String DEFAULT_UPLOAD_DIR = "/uploads";

    // 默认临时文件存储目录
    String DEFAULT_TEMP_DIR = "/tmp";

    // ----------------- 时间格式常量 -----------------
    // 默认日期格式
    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    // 默认日期时间格式
    String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // ----------------- 编码与字符集 -----------------
    // 默认字符编码
    String DEFAULT_CHARSET = "UTF-8";

    // ----------------- 自定义错误码 -----------------
    // 数据库操作错误
    int ERROR_DATABASE = 1001;

    // 网络请求错误
    int ERROR_NETWORK = 1002;

    // 业务逻辑错误
    int ERROR_BUSINESS = 1003;

    //bcrypt加密
    String BCRYPT_METHOD = "bcrypt";

    //pbkdf2加密
    String PBKDF2_METHOD = "pbkdf2";
}
