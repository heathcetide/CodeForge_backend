package com.cetide.codeforge.service.impl;

import com.cetide.codeforge.mapper.SocialLoginMapper;
import com.cetide.codeforge.model.entity.user.SocialLogin;
import com.cetide.codeforge.service.SocialLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SocialLoginServiceImpl extends ServiceImpl<SocialLoginMapper, SocialLogin>
    implements SocialLoginService {

    @Autowired
    private SocialLoginMapper socialLoginMapper;

    public SocialLogin saveSocialLogin(Long userId, String providerName, String providerUserId, String providerUsername, String accessToken, String refreshToken) {
        SocialLogin socialLogin = new SocialLogin();
        socialLogin.setUserId(userId);
        socialLogin.setProviderName(providerName);
        socialLogin.setProviderUserId(providerUserId);
        socialLogin.setProviderUsername(providerUsername);
        socialLogin.setAccessToken(accessToken);
        socialLogin.setRefreshToken(refreshToken);

        // 使用 MyBatis-Plus 的 save 方法自动保存
        this.save(socialLogin);
        return socialLogin;
    }
}
