package com.cetide.codeforge.controller;

import com.cetide.codeforge.common.security.JwtUtils;
import com.cetide.codeforge.exception.BusinessException;
import com.cetide.codeforge.model.dto.RegisterByEmail;
import com.cetide.codeforge.model.dto.request.UserRegisterEmailDTO;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.UserVO;
import com.cetide.codeforge.service.UserService;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.util.common.CaptchaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用户模块
 *
 * @author heathcetide
 */
@RestController
@RequestMapping("/api/users")
@Api(tags = "用户模块")
public class UserController {

    /**
     * 用户模块
     */
    private final UserService userService;

    /**
     * redis封装工具类
     */
    private final JwtUtils jwtUtils;

    /**
     * 静态日志
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return 发送状态
     */
    @PostMapping("/email/code")
    @ApiOperation("邮箱验证码发送[OK]")
    public ApiResponse<Boolean> sendEmailCode(@RequestParam String email) {
        Boolean isSuccess = userService.sendEmailCode(email);
        if (isSuccess) {
            return ApiResponse.success(true);
        } else {
            return ApiResponse.error(300, "发送失败");
        }
    }

    /**
     * 邮箱注册账号
     *
     * @param userRegisterEmailDTO 注册信息
     * @return 返回用户信息
     */
    @PostMapping("/register/email")
    @ApiOperation("邮箱注册账号[OK]")
    public ApiResponse<UserVO> registerEmail(@RequestBody UserRegisterEmailDTO userRegisterEmailDTO) {
        return ApiResponse.success(userService.registerByEmail(userRegisterEmailDTO));
    }

    /**
     * 邮箱登录
     *
     * @param registerByEmail 注册信息
     * @return 返回用户信息
     */
    @PostMapping("/login/email")
    @ApiOperation("邮箱登录账号[OK]")
    public ApiResponse<String> loginByEmail(
            @RequestBody RegisterByEmail registerByEmail,
            @RequestHeader Map<String, String> headers) throws Exception {
        String deviceId = headers.get("device-id");
        String deviceType = headers.get("device-type");
        String ipAddress = headers.get("ip-address");
        String userAgent = headers.get("user-agent");
        return ApiResponse.success(userService.loginByEmail(registerByEmail, deviceId, deviceType, ipAddress, userAgent));
    }

    @GetMapping("/info")
    @ApiOperation("根据token获取角色信息")
    public ApiResponse<UserVO> getUserInfoByToken(@RequestHeader("Authorization") String token) {
        try {
            if (token == null || token.isEmpty()){
                return ApiResponse.error(300, "未登录或token已过期");
            }
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String id = String.valueOf(jwtUtils.getUserIdFromToken(token));
            User currentUser = userService.getUserById(Long.valueOf(id));
            currentUser.setPassword(null);
            return ApiResponse.success(new UserVO().toUserVO(currentUser));
        }catch (Exception e){
            System.out.println("获取用户信息失败"+ e.getMessage());
            e.printStackTrace();
            return ApiResponse.error(300, "操作失败");
        }
    }

    @GetMapping("/info/level")
    @ApiOperation("根据token获取角色信息")
    public ApiResponse<UserVO> getUserLevelByToken(@RequestHeader("Authorization") String token) {
        try {
            if (token == null || token.isEmpty()){
                return ApiResponse.error(300, "未登录或token已过期");
            }
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String usernameFromToken = String.valueOf(jwtUtils.getUserIdFromToken(token));
            User currentUser = userService.getUserByUsername(usernameFromToken);
            currentUser.setPassword(null);
            UserVO userInfoLevelVO = new UserVO().toUserVO(currentUser);
            return ApiResponse.success(userInfoLevelVO);
        }catch (Exception e){
            System.out.println("获取用户信息失败"+ e.getMessage());
            e.printStackTrace();
            return ApiResponse.error(300, "操作失败");
        }
    }

    /**
     * 普通用户查看自己的信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/{username}")
    @ApiOperation("用户根据username查看自己的信息")
    public ApiResponse<UserVO> getUserById(@PathVariable String username) {
        try {
            // 日志记录
            logger.info("Fetching user information for username: {}", username);

            // 调用服务获取用户信息
            User user = userService.getUserByUsername(username);

            // 返回成功响应
            return ApiResponse.success(new UserVO().toUserVO(user));
        } catch (BusinessException e) {
            // 捕获自定义异常，返回友好提示
            logger.warn("Failed to fetch user information: {}", e.getMessage());
            return ApiResponse.error(404, e.getMessage());
        } catch (Exception e) {
            // 捕获未预期异常，记录日志并返回通用错误提示
            logger.error("Unexpected error occurred while fetching user information", e);
            return ApiResponse.error(500, "服务器内部错误");
        }
    }

    /**
     * 普通用户修改自己的信息
     *
     * @param user 修改后的用户信息
     * @return 用户信息
     */
    @PutMapping("/update")
    @ApiOperation("普通用户修改自己的信息")
    public ApiResponse<User> updateUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
        logger.info("Request received in updateUser: user={}, token={}", user, token); // 添加日志
        try {
            User updatedUser = userService.updateUserInfo(user, token);
            return ApiResponse.success(updatedUser);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(300, e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating user info", e);
            return ApiResponse.error(500, "服务器内部错误");
        }
    }

    /**
     * 用户退出登录
     *
     * @param token jwt信息
     * @return 退出登录状态
     */
    @PostMapping("/logout")
    @ApiOperation("用户退出登录")
    public ApiResponse<Void> logout(@RequestHeader("Authorization") String token) {
        userService.logoutUser(token);
        return ApiResponse.success(null);
    }

    /**
     * 生成校验二维码
     * @param session session
     * @param response response
     * @throws IOException 异常
     */
    @GetMapping("/captcha")
    @ApiOperation("生成校验二维码")
    public void getCaptcha(
            HttpSession session,
            HttpServletResponse response) throws IOException {
        String sessionId = session.getId();
        System.out.println("Session ID (getCaptcha): " + sessionId);
        String captchaText = CaptchaUtils.generateCaptchaText(6);
        System.out.println("Generated Captcha Text: " + captchaText);
        session.setAttribute("captcha", captchaText);

        // 生成验证码图片
        BufferedImage image = CaptchaUtils.generateCaptchaImage(captchaText);
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 禁用缓存
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        // 将图片写入响应流
        ImageIO.write(image, "png", response.getOutputStream());
    }

    /**
     * 验证校验二维码
     * @param request request
     * @param session session
     * @return 验证结果
     */
    @PostMapping("/verify-captcha")
    @ApiOperation("验证校验二维码")
    public ApiResponse<Boolean> verifyCaptcha(
             @RequestBody Map<String, String> request,
             HttpSession session) {
        String sessionId = session.getId();
        System.out.println("Session ID (verifyCaptcha): " + sessionId);
        String userCaptcha = request.get("captcha");
        String sessionCaptcha = (String) session.getAttribute("captcha");
        System.out.println("User Captcha: " + userCaptcha + " Session Captcha: " + sessionCaptcha);
        boolean success = userCaptcha != null && userCaptcha.equalsIgnoreCase(sessionCaptcha);
        System.out.println("Verification Result: " + success);
        return ApiResponse.success(success);
    }

    /**
     * 重置密码
     *
     * @param emailOrPhone 重置密码
     * @param newPassword 新密码
     * @return 重置密码
     */
    @PostMapping("/reset-password")
    @ApiOperation("重置密码-忘记密码的情况下，通过邮箱或手机号验证身份后设置新密码")
    public ApiResponse<Void> resetPassword(
              @RequestParam String emailOrPhone,
              @RequestParam String newPassword) {
        userService.resetPassword(emailOrPhone, newPassword);
        return ApiResponse.success(null);
    }

    /**
     * 修改密码
     *
     * @param token jwt
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改密码
     */
    @PostMapping("/change-password")
    @ApiOperation("修改密码-用户知道当前的密码，并通过身份验证来更改密码")
    public ApiResponse<Void> changePassword(@RequestHeader("Authorization") String token,
                       @RequestParam String oldPassword,
                       @RequestParam String newPassword) {
        userService.changePassword(token, oldPassword, newPassword);
        return ApiResponse.success(null);
    }

    /**
     * 上传头像
     *
     * @param file 文件
     * @param token jwt
     * @return 上传头像
     */
    @PostMapping("/upload-avatar")
    @ApiOperation("上传头像")
    public ApiResponse<String> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String token) {
        String avatarUrl = userService.uploadAvatar(file, token);
        return ApiResponse.success(avatarUrl);
    }

    /**
     * 注销用户
     *
     * @param token jwt
     * @return 注销用户
     */
    @PostMapping("/delete-account")
    @ApiOperation("注销用户")
    public ApiResponse<Void> deleteAccount(@RequestHeader("Authorization") String token) {
        userService.requestAccountDeletion(token);
        return ApiResponse.success(null);
    }

    /**
     * 开启二级验证码
     *
     * @param emailOrPhone 开启二级验证
     * @return 开启二级验证
     * @throws MessagingException 异常
     */
    @PostMapping("/send-verification-code")
    @ApiOperation("开启二级验证码")
    public ApiResponse<Void> sendVerificationCode(@RequestParam String emailOrPhone) throws MessagingException {
        userService.sendVerificationCode(emailOrPhone);
        return ApiResponse.success(null);
    }

    /**
     * 开启验证
     *
     * @param emailOrPhone 开启二级验证
     * @param code 验证码
     * @return 返回
     */
    @PostMapping("/verify")
    @ApiOperation("开启验证")
    public ApiResponse<Void> verifyCode(@RequestParam String emailOrPhone, @RequestParam String code) {
        userService.verifyCode(emailOrPhone, code);
        return ApiResponse.success(null);
    }

    /**
     * 搜索公开用户
     *
     * @param keyword 搜索用户
     * @return 返回用户列表
     */
    @GetMapping("/search")
    @ApiOperation("搜索公开用户")
    public ApiResponse<List<User>> searchUsers(@RequestParam String keyword,
                                               @RequestParam int page,
                                               @RequestParam int size) {
        return ApiResponse.success(userService.searchPublicUsers(keyword, page, size));
    }


    /**
     * 解绑第三方账号（未实现）
     *
     * @param token jwt
     * @param platform 平台
     * @return 解绑第三方账号
     */
    @PostMapping("/unbind-account")
    @ApiOperation("解绑第三方账号（未实现）")
    public ApiResponse<Void> unbindAccount(@RequestHeader("Authorization") String token, @RequestParam String platform) {
        userService.unbindThirdPartyAccount(token, platform);
        return ApiResponse.success(null);
    }
} 