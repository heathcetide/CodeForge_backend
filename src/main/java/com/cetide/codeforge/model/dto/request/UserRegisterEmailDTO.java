package com.cetide.codeforge.model.dto.request;

/**
 * 用户邮箱注册DTO
 *
 * @author heathcetide
 */
public class UserRegisterEmailDTO {

    private String email;

    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
