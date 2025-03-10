package com.cetide.blog.common.constants;

import com.cetide.blog.util.UUIDUtils;

/**
 * 用户常量信息
 *
 * @author heathcetide
 */
public interface UserConstants {

    String USER_CACHE_KEY = "user_key";

    Long TOKEN_CACHE_TIME = 3L;

    Long USER_CACHE_TIME = 3L;

    String SEND_EMAIL_CODE_SEND_TIME = "sendEmailCodeSendTime";

    Long SEND_EMAIL_CODE_TIME = 10L;

    String SEND_EMAIL_CODE = "send_email_code";

    String TOKEN_CACHE_KEY = "token_key";

    String NEW_USER_PASSWORD = "123456";

    String NEW_USER_NICKNAME = "Cuser - " + UUIDUtils.getUUID().substring(4);

    String NEW_USER_AVATAR = "https://cetide-1325039295.cos.ap-chengdu.myqcloud.com/b77339a6-6b52-40fb-9fb4-df04959c859dwordCloud-2412190816.png";

    String NEW_USER_LANGUAGE = "EN";

    Integer NEW_USER_GENDER = 0;

    String EMPTY_PERMISSIONS = "{}";

    String EMPTY_TAGS = "{}";

    String EMPTY_PASSWORD_HASH = "cetide";

    String EMPTY_READING_HISTORY = "{}";

    String EMPTY_ADDRESS = "成都";

    String EMPTY_FOLLOWERS = "{}";

    // 正则表达式: 至少一位字母（不分大小写），一位数字，一位特殊字符
    String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).+$";

    // 用户试用期
    Integer PROBATION_PERIOD = 30;

    // 用户审核定时任务前缀
    String QUARTZ_USER_PREFIX = "quartz_user_prefix_";

    // 用户预约推送时间偏移
    Integer RESERVE_TASK_OFFSET = -15;

}
