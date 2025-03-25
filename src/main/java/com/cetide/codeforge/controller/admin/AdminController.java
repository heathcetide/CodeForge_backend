package com.cetide.codeforge.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.common.security.JwtUtils;
import com.cetide.codeforge.controller.UserController;
import com.cetide.codeforge.model.dto.request.AdminCreateDTO;
import com.cetide.codeforge.model.dto.request.AdminLoginDTO;
import com.cetide.codeforge.model.dto.request.AdminUpdateDTO;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.UserVO;
import com.cetide.codeforge.service.UserService;
import com.cetide.codeforge.util.common.CaptchaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户模块
 *
 * @author heathcetide
 */
@RestController
@RequestMapping("/api/admin/users")
@Api(tags = "管理员模块")
public class AdminController {

    /**
     * 用户模块
     */
    private final UserService userService;

    /**
     * 静态日志
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 邮箱登录
     *
     * @return 返回用户信息
     */
    @PostMapping("/login")
    @ApiOperation("管理员账号登录")
    public ApiResponse<String> loginByEmail(
            @RequestBody AdminLoginDTO adminLoginDTO) {
        return ApiResponse.success(userService.adminLogin(adminLoginDTO));
    }

    /**
     * 根据token获取信息
     */
    @GetMapping("/info")
    @ApiOperation("管理员token获取角色信息")
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
        String captchaText = CaptchaUtils.generateCaptchaText(6);
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
     * 分页获取管理员列表
     */
    @GetMapping
    @ApiOperation("分页获取管理员列表")
    public ApiResponse<Page<UserVO>> listAdmins(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Page<User> userPage = new Page<>(page, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("role", "ADMIN");
        IPage<User> result = userService.page(userPage, wrapper);
        Page<UserVO> voPage = new Page<>();
        voPage.setCurrent(result.getCurrent());
        voPage.setSize(result.getSize());
        voPage.setTotal(result.getTotal());
        voPage.setRecords(result.getRecords().stream()
                .map(user -> new UserVO().toUserVO(user))
                .collect(Collectors.toList()));
        return ApiResponse.success(voPage);
    }

    /**
     * 获取指定管理员的详细信息
     */
    @GetMapping("/{id}")
    @ApiOperation("获取指定管理员详细信息")
    public ApiResponse<UserVO> getAdmin(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return ApiResponse.error(404, "管理员不存在");
        }
        return ApiResponse.success(new UserVO().toUserVO(user));
    }

    /**
     * 创建新的管理员账号
     */
    @PostMapping
    @ApiOperation("创建新的管理员账号")
    public ApiResponse<Boolean> createAdmin(@RequestBody AdminCreateDTO adminCreateDTO) {
        User admin = new User();
        admin.setUsername(adminCreateDTO.getUsername());
        // 密码加密建议在 service 层处理
        admin.setPassword(adminCreateDTO.getPassword());
        admin.setRole("ADMIN");
        // 根据需要设置其他属性...
        boolean result = userService.save(admin);
        return result ? ApiResponse.success(true) : ApiResponse.error(400, "创建管理员失败");
    }

    /**
     * 更新管理员信息
     */
    @PutMapping("/{id}")
    @ApiOperation("更新管理员信息")
    public ApiResponse<Boolean> updateAdmin(@PathVariable Long id, @RequestBody AdminUpdateDTO adminUpdateDTO) {
        User admin = userService.getById(id);
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            return ApiResponse.error(404, "管理员不存在");
        }
        admin.setUsername(adminUpdateDTO.getUsername());
        if (adminUpdateDTO.getPassword() != null && !adminUpdateDTO.getPassword().trim().isEmpty()) {
            admin.setPassword(adminUpdateDTO.getPassword());
        }
        // 根据需要更新其他属性...
        boolean result = userService.updateById(admin);
        return result ? ApiResponse.success(true) : ApiResponse.error(400, "更新管理员失败");
    }

    /**
     * 删除指定管理员账号
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除指定管理员账号")
    public ApiResponse<Boolean> deleteAdmin(@PathVariable Long id) {
        User admin = userService.getById(id);
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            return ApiResponse.error(404, "管理员不存在");
        }
        boolean result = userService.removeById(id);
        return result ? ApiResponse.success(true) : ApiResponse.error(400, "删除管理员失败");
    }
} 