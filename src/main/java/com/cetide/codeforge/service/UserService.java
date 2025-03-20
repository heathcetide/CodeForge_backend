package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.RegisterByEmail;
import com.cetide.codeforge.model.dto.request.UserRegisterEmailDTO;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.UserVO;
import com.cetide.codeforge.common.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    User getUserById(Long id);

    void logout(Long userId);

    User updateUser(Long id, User updateUser);

    // 管理员专属操作接口
    boolean createUser(User user);

    boolean updateUserByAdmin(Long id, User user);

    boolean deleteUserByAdmin(Long id);

    Long countNewUsers(String startDate, String endDate);

    Long countActiveUsers(String startDate, String endDate);

    void logoutUser(String token);

    void resetPassword(String emailOrPhone, String newPassword);

    void changePassword(String token, String oldPassword, String newPassword);

    String uploadAvatar(MultipartFile file, String token);

    void requestAccountDeletion(String token);

    void sendVerificationCode(String emailOrPhone) throws MessagingException;

    void verifyCode(String emailOrPhone, String code);

    List<User> searchPublicUsers(String keyword, int page, int size);

    Map<String, Object> getUserStatistics(String token);

    void unbindThirdPartyAccount(String token, String platform);

    void enableTwoFactorAuth(String token, String secretKey);

    void verifyTwoFactorAuth(String token, String verificationCode);

    User getUserByUsername(String username);

    User getPublicUserByUsername(String username);

    Long getUserCount();

    List<User> getPopularUsers();

    Boolean sendEmailCode(String email);

    UserVO registerByEmail(UserRegisterEmailDTO userRegisterEmailDTO);

    String loginByEmail(RegisterByEmail registerByEmail, String deviceId, String deviceType, String ipAddress, String userAgent) throws Exception;

    Page<User> getUsers(Page<User> userPage, String keyword);

    List<Map<String, Object>> countUsersByStatus();

    List<Map<String, Object>> countUsersByRole();

    List<Map<String, Object>> countUsersByGender();

    List<Map<String, Object>> countUsersByAge();

    User updateUserInfo(User user, String token);
}