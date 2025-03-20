package com.cetide.codeforge.common.constants;

/**
 * 缓存的key 常量
 *
 * @author heathcetide
 */
public interface CacheConstants {

    /**
     * 登录用户 redis key
     */
    String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 短信验证码 redis key
     */
    String ALIYUN_SMS_CODE_KEY = "aliyun:sms:code:";

    /**
     * 重置密码时校验凭证 redis key
     */
    String RESET_PWD_CERT_KEY = "reset:pwd:cert:";

    /**
     * 微信登录用户未注册时缓存 redis key
     */
    String UNREGISTER_CODE_KEY = "unregister:wechat:code:";

    /**
     * 省份缓存 redis key
     */
    String PROVINCE_NAME = "province";

    /**
     * 服务基础配置缓存 redis key
     */
    String BASE_CONFIG_KEY = "config";

    /**
     * 重新提交注册缓存 redis key
     */
    String REGISTER_AGAIN_KEY = "register:again:";

    /**
     * 栏目数据缓存 redis key
     */
    String COLUMN_KEY = "column";
}
