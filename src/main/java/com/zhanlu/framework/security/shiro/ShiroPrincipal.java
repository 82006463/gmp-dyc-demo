package com.zhanlu.framework.security.shiro;

import com.zhanlu.framework.security.entity.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 自定义认证主体
 *
 * @author yuqs
 * @since 0.1
 */
public class ShiroPrincipal implements Serializable {

    private static final long serialVersionUID = 1428196040744555722L;

    //用户对象
    private User user;
    //用户权限列表
    private Set<String> authorities = new HashSet<>();
    //用户角色列表
    private Set<String> roles = new HashSet<>();
    //是否已授权。如果已授权，则不需要再从数据库中获取权限信息，减少数据库访问
    //这里会导致修改权限时，需要重新登录方可有效
    private boolean isAuthorized = false;

    /**
     * 构造函数，参数为User对象
     * 根据User对象属性，赋值给Principal相应的属性上
     *
     * @param user
     */
    public ShiroPrincipal(User user) {
        this.user = user;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public Long getId() {
        return this.user.getId();
    }

    /**
     * <shiro:principal/>标签显示中文名称
     */
    @Override
    public String toString() {
        return this.user.getFullname();
    }
}
