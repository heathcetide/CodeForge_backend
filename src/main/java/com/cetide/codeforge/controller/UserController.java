package com.cetide.codeforge.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.common.auth.GithubOAuth;
import com.cetide.codeforge.common.security.JwtUtils;
import com.cetide.codeforge.model.dto.RegisterByEmail;
import com.cetide.codeforge.model.dto.request.UserRegisterEmailDTO;
import com.cetide.codeforge.model.entity.user.SocialLogin;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.UserVO;
import com.cetide.codeforge.service.SocialLoginService;
import com.cetide.codeforge.service.UserService;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.util.common.CaptchaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
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
     * Github登录工具类
     */
    private final GithubOAuth githubOAuth;

    /**
     * 第三方平台服务类
     */
    private final SocialLoginService socialLoginService;

    /**
     * 静态日志
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, JwtUtils jwtUtils, GithubOAuth githubOAuth, SocialLoginService socialLoginService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.githubOAuth = githubOAuth;
        this.socialLoginService = socialLoginService;
    }


    /**
     * OAuth 对接Github 登录
     */
    @GetMapping("/oauth2/code/github")
    public void callback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        // 使用授权码换取 access_token
        String accessToken = githubOAuth.getAccessTokenFromGithub(code);

        if (accessToken != null) {
            // 使用 access_token 获取 GitHub 用户信息
            Map<String, Object> userInfo = githubOAuth.getUserInfoFromGithub(accessToken);
            for (Map.Entry<String, Object> entry : userInfo.entrySet()) {
                System.out.println("-----------");
                System.out.println(entry.getKey()+" "+entry.getValue());
            }
            // 获取 GitHub 用户的唯一标识符
            Integer providerUserId = (Integer) userInfo.get("id");
            String providerUsername = (String) userInfo.get("login");
            String providerEmail = (String) userInfo.get("email");
            String avatarUrl = (String) userInfo.get("avatar_url");

            LambdaQueryWrapper<SocialLogin> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SocialLogin::getProviderUserId, providerUserId);
            queryWrapper.eq(SocialLogin::getProviderName, "github");
            // 查找该 GitHub 用户是否已经在数据库中存在
            SocialLogin existingSocialLogin = socialLoginService.getOne(queryWrapper);
            if (existingSocialLogin == null) {
                // 如果不存在，先通过 UserService 创建新的用户记录
                User newUser = userService.createUserFromSocialLogin(providerUsername, providerEmail, avatarUrl);
                // 获取新创建的用户 ID
                Long userId = newUser.getId();
                // 然后创建新的社交登录记录
                SocialLogin newSocialLogin = new SocialLogin();
                newSocialLogin.setUserId(userId);
                newSocialLogin.setProviderName("github");
                newSocialLogin.setProviderUserId(String.valueOf(providerUserId));
                newSocialLogin.setProviderUsername(providerUsername);
                newSocialLogin.setAccessToken(accessToken);
                newSocialLogin.setRefreshToken(null);  // 如果没有 refresh token 可以设置为 null
                // 保存社交登录记录
                socialLoginService.save(newSocialLogin);
            }
            User user = userService.getById(existingSocialLogin.getUserId());
            // 生成 JWT token
            String jwtToken = jwtUtils.generateToken(String.valueOf(user.getId()));
            // 构建 URL，携带 JWT 和用户信息
            String redirectUrl = "http://localhost:7070?token=" + jwtToken+ "&code=" + code;
            // 重定向到前端页面，并将 token 和用户信息作为查询参数传递
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("http://localhost:7070/login-failed"); // 登录失败时跳转
        }
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

    /**
     * 根据token获取信息
     */
    @GetMapping("/info")
    @ApiOperation("根据token获取角色信息")
    public ApiResponse<UserVO> getUserInfoByToken() {
        try {
            User currentUser = AuthContext.getCurrentUser();
            currentUser.setPassword(null);
            return ApiResponse.success(new UserVO().toUserVO(currentUser));
        }catch (Exception e){
            System.out.println("获取用户信息失败"+ e.getMessage());
            e.printStackTrace();
            return ApiResponse.error(300, "操作失败");
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
     */
    @PostMapping("/logout")
    @ApiOperation("用户退出登录")
    public ApiResponse<String> logout() {
        String currentToken = AuthContext.getCurrentToken();
        userService.logoutUser(currentToken);
        return ApiResponse.success("退出登录成功");
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
     * @return 上传头像
     */
    @PostMapping("/upload-avatar")
    @ApiOperation("上传头像")
    public ApiResponse<String> uploadAvatar(
            @RequestParam("file") MultipartFile file) throws IOException {
        String avatarUrl = userService.uploadAvatar(file, AuthContext.getCurrentToken());
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
} 