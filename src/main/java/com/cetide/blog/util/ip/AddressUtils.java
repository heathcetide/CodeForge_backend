package com.cetide.blog.util.ip;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import com.cetide.blog.common.constants.Constants;
import com.cetide.blog.util.StringUtils;
import com.cetide.blog.util.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 *
 * @author heathcetide
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            JSONObject obj = JSON.parseObject(rspStr);
            String region = obj.getString("pro");
            String city = obj.getString("city");
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return UNKNOWN;
    }

    /**
     * Main 方法用于测试地址解析功能
     */
    public static void main(String[] args) {
        // 测试内网 IP
        String internalIp = "192.168.1.100";
        String internalAddress = getRealAddressByIP(internalIp);
        System.out.println("Internal IP (" + internalIp + "): " + internalAddress);

        // 测试外网 IP（例如 8.8.8.8）
        String externalIp = "180.84.30.195";
        String externalAddress = getRealAddressByIP(externalIp);
        System.out.println("External IP (" + externalIp + "): " + externalAddress);
    }
}
