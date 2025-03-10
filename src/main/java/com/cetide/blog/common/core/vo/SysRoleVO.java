package com.cetide.blog.common.core.vo;


public class SysRoleVO {

    private String roleName;

    private String roleKey;

    private String status;

    private Integer roleSort;

    public SysRoleVO(){

    }

    public SysRoleVO(String roleName, String roleKey, String status, Integer roleSort) {
        this.roleName = roleName;
        this.roleKey = roleKey;
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }
}
