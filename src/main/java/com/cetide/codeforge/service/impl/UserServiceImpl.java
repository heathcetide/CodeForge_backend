package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.common.security.JwtUtils;
import com.cetide.codeforge.exception.BusinessException;
import com.cetide.codeforge.mapper.UserMapper;
import com.cetide.codeforge.model.dto.RegisterByEmail;
import com.cetide.codeforge.model.dto.request.UserRegisterEmailDTO;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.UserVO;
import com.cetide.codeforge.service.EmailService;
import com.cetide.codeforge.service.UserService;
import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.util.PasswordEncoder;
import com.cetide.codeforge.util.common.RedisUtils;
import com.cetide.codeforge.util.SensitiveDataUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.cetide.codeforge.common.constants.RoleConstants.USER;
import static com.cetide.codeforge.common.constants.UserConstants.*;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private EmailService emailService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MessageSource messageSource;

    // 静态日志实例
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    /**
     * 分页获取用户列表
     *
     * @param userPage 分页对象
     * @param keyword  搜索关键字
     * @return 分页的用户列表
     */
    @Override
    public Page<User> getUsers(Page<User> userPage, String keyword) {
        return userMapper.selectUsers(userPage, keyword);
    }

    @Override
    public List<Map<String, Object>> countUsersByStatus() {
        return userMapper.selectStatusUserCount();
    }

    @Override
    public List<Map<String, Object>> countUsersByRole() {
        return userMapper.selectRoleCount();
    }

    @Override
    public List<Map<String, Object>> countUsersByGender() {
        return userMapper.selectGenderCount();
    }

    @Override
    public List<Map<String, Object>> countUsersByAge() {
        return userMapper.selectAgeCount();
    }

    /**
     * 更新用户信息
     *
     * @param user 包含更新信息的用户对象
     * @return 更新后的用户对象
     * @throws IllegalArgumentException 如果用户名为空或用户不存在
     * @throws RuntimeException         如果更新数据库失败
     */
    @Override
    @Transactional
    public User updateUserInfo(User user, String token) {
        if (user == null || StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException(getMessage("error.username.empty"));
        }

        // 从缓存或数据库中获取用户信息
        User existingUser = getUserByUsername(user.getUsername());
        if (existingUser == null) {
            throw new IllegalArgumentException(getMessage("error.user.notfound", user.getUsername()));
        }

        // 验证当前用户是否有权限修改
        User currentUser = getUserByUsername(String.valueOf(jwtUtils.getUserIdFromToken(token)));
        if (!Objects.equals(currentUser.getId(), existingUser.getId())) {
            throw new IllegalArgumentException(getMessage("error.user.permission.denied"));
        }

        // 确保 ID 不被修改
        user.setId(existingUser.getId());
        // 防止敏感字段被修改
        user.setPassword(existingUser.getPassword());
        user.setRole(existingUser.getRole());

        User updatedUser = userMapper.selectByUsername(user.getUsername());
        redisUtils.delete(USER_CACHE_KEY + updatedUser.getUsername());
        redisUtils.delete(USER_CACHE_KEY + updatedUser.getId());

        scheduler.schedule(() -> {
            redisUtils.delete(USER_CACHE_KEY + updatedUser.getUsername());
            redisUtils.delete(USER_CACHE_KEY + updatedUser.getId());
        }, 500, TimeUnit.MILLISECONDS);

        // 更新数据
        return getUserByUsername(user.getUsername());
    }

    @Override
    public User createUserFromSocialLogin(String providerUsername, String providerEmail, String avatarUrl) {
        User user = buildSocialNewUser(providerUsername, providerEmail, avatarUrl);
        try {
            if (userMapper.insert(user) != 1) {
                throw new BusinessException("注册失败, 请联系站长");
            }
            if (providerEmail != null && !providerEmail.isEmpty()) {
                emailService.sendWelcomeEmail(user.getUsername(), providerEmail);
            }
            // 缓存用户信息
            redisUtils.set(USER_CACHE_KEY + user.getId(), user, USER_CACHE_TIME);
            redisUtils.set(USER_CACHE_KEY + user.getUsername(), user, USER_CACHE_TIME);
            return user;
        } catch (Exception e) {
            throw new BusinessException("操作失败, 请联系站长");
        }
    }

    /**
     * 更新用户信息
     *
     * @param id         用户ID
     * @param updateUser 包含要更新字段的用户对象
     * @return 更新后的用户对象
     * @throws BusinessException 如果邮箱或手机号已被注册
     */
    @Transactional
    @Override
    public User updateUser(Long id, User updateUser) {
        User user = getUserById(id);

        if (updateUser.getEmail() != null && !user.getEmail().equals(updateUser.getEmail())
                && userMapper.existsByEmail(updateUser.getEmail())) {
            throw new BusinessException("邮箱已被注册");
        }

        if (updateUser.getPhone() != null && !user.getPhone().equals(updateUser.getPhone())
                && userMapper.existsByPhone(updateUser.getPhone())) {
            throw new BusinessException("手机号已被注册");
        }
        userMapper.updateById(user);
        // 更新缓存
        redisUtils.set(USER_CACHE_KEY + id, user, USER_CACHE_TIME);
        return user;
    }


    /**
     * 用户邮箱注册
     *
     * @param userRegisterEmailDTO 用户通过邮箱注册请求对象，包含邮箱和验证码
     * @return 注册成功的用户视图对象
     */
    @Override
    @Transactional
    public UserVO registerByEmail(UserRegisterEmailDTO userRegisterEmailDTO) {
        String email = userRegisterEmailDTO.getEmail();
        String redisKeyCode = generateRedisKey(email, SEND_EMAIL_CODE);
        if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKeyCode))) {
            throw new BusinessException("验证码过期, 请重新发送验证码");
        }
        if (!userRegisterEmailDTO.getCode().equals(redisTemplate.opsForValue().get(redisKeyCode))) {
            throw new BusinessException("验证码错误, 请重新发送验证码");
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail, email);
        User userByEmail = userMapper.selectOne(userLambdaQueryWrapper);
        if (null != userByEmail) {
            throw new BusinessException("邮箱已被注册");
        }
        String username = email.split("@")[0] + email.split("@")[1]; // 获取邮箱的前缀部分
        if (userMapper.existsByUsername(username)) {
            // 如果邮箱前缀已被占用，加随机数或后缀
            username += "_" + new Random().nextInt(9000) + 1000;
        }
        User user = buildNewUser(username, userRegisterEmailDTO.getEmail());
        try {
            if (userMapper.insert(user) != 1) {
                throw new BusinessException("注册失败, 请联系站长");
            }
            emailService.sendWelcomeEmail(user.getUsername(), email);
            // 缓存用户信息
            redisUtils.set(USER_CACHE_KEY + user.getId(), user, USER_CACHE_TIME);
            redisUtils.set(USER_CACHE_KEY + user.getUsername(), user, USER_CACHE_TIME);
            return new UserVO().toUserVO(user);
        } catch (Exception e) {
            throw new BusinessException("操作失败, 请联系站长");
        }
    }

    /**
     * 用户邮箱登录
     *
     * @param registerByEmail 用户通过邮箱登录请求对象，包含邮箱和验证码
     * @param deviceId        登录设备ID
     * @param deviceType      登录设备类型
     * @param ipAddress       登录设备的IP地址
     * @param userAgent       登录设备的用户代理信息
     * @return 登录成功返回Token，否则返回错误信息
     */
    @Override
    public String loginByEmail(RegisterByEmail registerByEmail, String deviceId, String deviceType, String ipAddress, String userAgent) throws Exception {
        String email = registerByEmail.getEmail();
        // 添加邮箱格式校验
        if (!isValidEmail(email)) {
            throw new BusinessException("邮箱格式不正确");
        }
        if (!userMapper.existsByEmail(email)) {
            throw new BusinessException("邮箱未注册");
        }
        String redisKeyCode = generateRedisKey(email, SEND_EMAIL_CODE);
        if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKeyCode))) {
            throw new BusinessException("操作失败，请重新发送验证码");
        }
        if (!registerByEmail.getCode().equals(redisTemplate.opsForValue().get(redisKeyCode))) {
            throw new BusinessException("验证码错误");
        }
        User user = userMapper.selectByEmail(registerByEmail.getEmail());
        String token = jwtUtils.generateToken(String.valueOf(user.getId()));
        // 缓存token和用户信息
        redisUtils.set(TOKEN_CACHE_KEY + user.getId(), token, TOKEN_CACHE_TIME);
        redisUtils.set(USER_CACHE_KEY + user.getUsername(), user, USER_CACHE_TIME);
        redisUtils.set(USER_CACHE_KEY + user.getId(), user, USER_CACHE_TIME);
        emailService.sendWelcomeEmail(user.getUsername(), email);
        return token;
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 用户邮箱
     * @return 是否发送成功
     */
    @Override
    public Boolean sendEmailCode(String email) {
        String redisKeyCode = generateRedisKey(email, SEND_EMAIL_CODE);
        String redisKeySendTime = generateRedisKey(email, SEND_EMAIL_CODE_SEND_TIME);

        // 1.检查是否在发送间隔内（例如 1 分钟）
        Boolean isSent = redisTemplate.opsForValue().setIfAbsent(redisKeySendTime, "1", SEND_INTERVAL, TimeUnit.MINUTES);
        if (Boolean.FALSE.equals(isSent)) {
            throw new BusinessException("验证码发送过于频繁，请稍后再试");
        }

        // 2️.生成验证码
        String code = generateRandomCode();
        try {
            // 3.1存储验证码（10 分钟有效期）
            redisTemplate.opsForValue().set(redisKeyCode, code, SEND_EMAIL_CODE_TIME, TimeUnit.MINUTES);
            // 3.2设置发送时间间隔（1 分钟有效期）
            redisTemplate.opsForValue().set(redisKeySendTime, "1", SEND_INTERVAL, TimeUnit.MINUTES);
            // 4.发送邮件（带重试机制）
            emailService.sendEmailWithRetry(email, code, 3, 1000);
            return true;
        } catch (RedisConnectionFailureException redisEx) {
            throw new RuntimeException("系统错误，请稍后重试");
        } catch (Exception e) {
            // 删除 Redis 中的数据，防止冗余
            redisTemplate.delete(redisKeyCode);
            redisTemplate.delete(redisKeySendTime);
            return false;
        }
    }


    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户对象
     * @throws BusinessException 如果用户不存在
     */
    @Override
    public User getUserById(Long id) {
        // 先从缓存获取
        redisUtils.delete(USER_CACHE_KEY + id);
        Object cached = redisUtils.get(USER_CACHE_KEY + id);
        if (cached != null) {
            return (User) cached;
        }

        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 写入缓存
        redisUtils.set(USER_CACHE_KEY + id, user, USER_CACHE_TIME);
        return user;
    }

    /**
     * 用户退出登录
     *
     * @param userId 用户ID
     */
    @Transactional
    @Override
    public void logout(Long userId) {
        // 删除token和用户缓存
        redisUtils.delete(TOKEN_CACHE_KEY + userId);
        redisUtils.delete(USER_CACHE_KEY + userId);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户对象
     * @throws BusinessException 如果用户不存在
     */
    @Override
    public User getUserByUsername(String username) {
        // 参数校验
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        // 构建缓存键
        String cacheKey = USER_CACHE_KEY + username;

        // 从缓存获取数据，避免缓存穿透
        User cached = (User) redisUtils.get(cacheKey);
        if (cached != null) {
            logger.info("Cache hit for username: {}", username);
            // 深拷贝防止缓存对象被误修改
            return SerializationUtils.clone(cached);
        }
        synchronized (("CACHE_LOCK_" + username).intern()) {
            // 双重检查，确保其他线程未更新缓存
            cached = (User) redisUtils.get(cacheKey);
            if (cached != null) {
                logger.info("Cache hit (after lock) for username: {}", username);
                return SerializationUtils.clone(cached);
            }

            // 从数据库查询
            logger.info("Cache miss for username: {}. Querying database...", username);
            User user = userMapper.selectByUsername(username);

            // 如果用户不存在，抛出异常
            if (user == null) {
                logger.warn("User not found for username: {}. Adding placeholder to cache.", username);
                redisUtils.set(cacheKey, "null", 60); // 缓存空值 60 秒
                throw new BusinessException("用户不存在");
            }

            // 写入缓存，设置超时时间，防止缓存雪崩
            // 写入缓存时，增加随机过期时间
            long randomExpiry = USER_CACHE_TIME + new Random().nextInt(300); // 随机多加 0-300 秒
            redisUtils.set(cacheKey, SerializationUtils.clone(user), randomExpiry);
            logger.info("User email: {}", SensitiveDataUtils.maskEmail(user.getEmail()));

            return user;
        }
    }

    /**
     * 获取公开的用户信息
     *
     * @param username 用户名
     * @return 用户对象（仅包含公开字段）
     */
    @Override
    public User getPublicUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 获取用户总数
     *
     * @return 用户总数
     */
    @Override
    public Long getUserCount() {
        return userMapper.selectUserCount();
    }

    /**
     * 获取热门用户
     *
     * @return 热门用户列表
     */
    @Override
    public List<User> getPopularUsers() {
        return userMapper.selectPopularUsers();
    }

    /**
     * 搜索公开用户
     *
     * @param keyword 搜索关键字
     * @param page    页码，从1开始
     * @param size    每页大小
     * @return 符合条件的用户列表
     */
    @Override
    public List<User> searchPublicUsers(String keyword, int page, int size) {
        Page<User> userPage = new Page<>(page, size);
        return userMapper.searchPublicUsers(keyword, userPage);
    }

    /**
     * 创建用户
     *
     * @param user 用户对象
     * @return 是否创建成功
     */
    @Override
    @Transactional
    public boolean createUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            user.setUsername(NEW_USER_NICKNAME);
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(NEW_USER_PASSWORD);
        }
        if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
            user.setAvatar("https://cetide-1325039295.cos.ap-chengdu.myqcloud.com/a3e4bf04-581d-4615-b3ca-f690ab88c8ebwordCloud-6984284909.png");
        }
        user.setLastLoginTime(new Date());
        // 确保创建的用户字段合法性
        return save(user);
    }


    /**
     * 管理员更新用户信息
     *
     * @param id   用户ID
     * @param user 更新后的用户信息
     * @return 是否更新成功
     */
    @Override
    @Transactional
    public boolean updateUserByAdmin(Long id, User user) {
        // 通过 ID 查找用户并更新
        User existingUser = getById(id);
        if (existingUser.getDeleted() == 1) {
            throw new BusinessException("该用户已经被删除，无法修改信息");
        }
        user.setId(id);
        return updateById(user);
    }

    /**
     * 管理员删除用户
     *
     * @param id 用户ID
     * @return 是否删除成功
     */
    @Override
    @Transactional
    public boolean deleteUserByAdmin(Long id) {
        return userMapper.deleteUserById(id);
    }


    /**
     * 统计指定时间范围内的新增用户数
     *
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @return 新增用户数
     */
    @Override
    public Long countNewUsers(String startDate, String endDate) {
        return lambdaQuery()
                .ge(startDate != null, User::getCreatedAt, startDate)
                .le(endDate != null, User::getCreatedAt, endDate)
                .count();
    }

    /**
     * 统计指定时间范围内的活跃用户数
     *
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @return 活跃用户数
     */
    @Override
    public Long countActiveUsers(String startDate, String endDate) {
        return lambdaQuery()
                .ge(startDate != null, User::getLastLoginTime, startDate)
                .le(endDate != null, User::getLastLoginTime, endDate)
                .count();
    }

    /**
     * 用户退出登录
     *
     * @param token 用户的Token
     */
    @Override
    @Transactional
    public void logoutUser(String token) {
        // 假设使用 Redis 保存 Token，移除 Token
        redisTemplate.delete("TOKEN_" + token);
    }

    /**
     * 重置用户密码
     *
     * @param emailOrPhone 用户的邮箱或手机号
     * @param newPassword  新密码
     */
    @Override
    public void resetPassword(String emailOrPhone, String newPassword) {
        User user = lambdaQuery()
                .eq(User::getEmail, emailOrPhone)
                .or()
                .eq(User::getPhone, emailOrPhone)
                .one();
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        user.setPassword(PasswordEncoder.encode(newPassword)); // 加密密码
        updateById(user);
    }

    /**
     * 用户修改密码
     *
     * @param token       用户的Token
     * @param oldPassword 用户的旧密码
     * @param newPassword 用户的新密码
     */
    @Override
    public void changePassword(String token, String oldPassword, String newPassword) {
        // 获取用户信息
        Long userId = jwtUtils.getUserIdFromToken(token);
        User user = getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        // 验证旧密码
        if (!PasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("旧密码不正确");
        }
        // 设置新密码
        user.setPassword(PasswordEncoder.encode(newPassword));
        updateById(user);
    }

    /**
     * 上传用户头像
     *
     * @param file  用户上传的头像文件
     * @param token 用户的Token
     * @return 上传后的头像URL
     */
    @Override
    public String uploadAvatar(MultipartFile file, String token) {
        // 上传到云存储或本地存储
//        String avatarUrl = fileStorageService.uploadFile(file, "avatars/");

        // 更新用户信息
        Long userId = jwtUtils.getUserIdFromToken(token);
        User user = getById(userId);
        if (user != null) {
//            user.setAvatar(avatarUrl);
            updateById(user);
        }

//        return avatarUrl;
        return "https://cetide-1325039295.cos.ap-chengdu.myqcloud.com/a3e4bf04-581d-4615-b3ca-f690ab88c8ebwordCloud-6984284909.png";
    }

    /**
     * 用户申请注销账号
     *
     * @param token 用户的Token
     */
    @Override
    @Transactional
    public void requestAccountDeletion(String token) {
        Long userId = jwtUtils.getUserIdFromToken(token);
        User user = getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        // 标记为软删除
        user.setDeleted(1);
        updateById(user);
    }

    /**
     * 发送验证码到用户邮箱或手机号
     *
     * @param emailOrPhone 用户的邮箱或手机号
     * @throws MessagingException 如果邮件发送失败
     */
    @Override
    public void sendVerificationCode(String emailOrPhone) throws MessagingException {
        // 生成验证码
        String code = generateRandomCode();
        if (emailOrPhone.contains("@")) {
            // 通过邮箱发送
//            emailNotificationSender.sendEmail(emailOrPhone, "验证码", "您的验证码是：" + code);
        } else {
            // 通过短信发送
//            smsService.sendSms(emailOrPhone, "您的验证码是：" + code);
        }

        // 保存到 Redis（5 分钟有效）
        redisTemplate.opsForValue().set("VERIFICATION_CODE_" + emailOrPhone, code, 5, TimeUnit.MINUTES);
    }


    @Override
    public void verifyCode(String emailOrPhone, String code) {
        String storedCode = (String) redisTemplate.opsForValue().get("VERIFICATION_CODE_" + emailOrPhone);
        if (storedCode == null || !storedCode.equals(code)) {
            throw new IllegalArgumentException("验证码错误或已过期");
        }

        // 验证通过，删除验证码
        redisTemplate.delete("VERIFICATION_CODE_" + emailOrPhone);
    }

    @Override
    public Map<String, Object> getUserStatistics(String token) {
        Long userId = jwtUtils.getUserIdFromToken(token);
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", count());
//        stats.put("activeUsers", lambdaQuery().eq(User::getStatus, 1).count());
//        stats.put("loginCount", operationLogService.getLoginCountByUserId(userId));
//        stats.put("lastLogin", operationLogService.getLastLoginTimeByUserId(userId));
        return stats;
    }

    @Override
    public void enableTwoFactorAuth(String token, String secretKey) {
        Long userId = jwtUtils.getUserIdFromToken(token);
        User user = getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
//        user.setTwoFactorSecret(secretKey);
//        user.setTwoFactorEnabled(true);
        updateById(user);
    }


    @Override
    public void verifyTwoFactorAuth(String token, String verificationCode) {
        Long userId = jwtUtils.getUserIdFromToken(token);
        User user = getById(userId);
//        if (user == null || !user.getTwoFactorEnabled()) {
//            throw new IllegalArgumentException("双因素认证未启用");
//        }

        // 验证码校验逻辑
//        if (!twoFactorAuthService.verifyCode(user.getTwoFactorSecret(), verificationCode)) {
//            throw new IllegalArgumentException("双因素认证验证码错误");
//        }
    }


    /**
     * 生成安全的六位验证码
     *
     * @return
     */
    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        int length = 6; // 验证码长度
        StringBuilder sb = new StringBuilder(length);
        String chars = "0123456789"; // 可扩展为字母和数字组合，如 "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * 构建md5加密的redis-key
     *
     * @param email
     * @param prefix
     * @return
     */
    private String generateRedisKey(String email, String prefix) {
        String hashedEmail = DigestUtils.md5DigestAsHex(email.getBytes(StandardCharsets.UTF_8)); // 使用 Spring 提供的 MD5 工具
        return prefix + hashedEmail;
    }


    /**
     * 验证邮箱格式是否正确
     *
     * @param email 邮箱地址
     * @return 是否为合法邮箱
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        return email != null && email.matches(emailRegex);
    }

    public boolean validateToken(String token) {
        Long userId = jwtUtils.getUserIdFromToken(token);
        if (userId == null) {
            return false;
        }

        // 检查token是否在缓存中
        Object cachedToken = redisUtils.get(TOKEN_CACHE_KEY + userId);
        return token.equals(cachedToken);
    }

    /**
     * 获取国际化消息
     *
     * @param key  key
     * @param args 参数
     * @return 返回结果
     */
    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}