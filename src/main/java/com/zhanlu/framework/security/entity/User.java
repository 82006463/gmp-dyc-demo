package com.zhanlu.framework.security.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类，继承抽象安全实体类
 *
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "SEC_USER")
public class User extends IdEntity {

    private static final long serialVersionUID = 7446802057673100315L;

    //系统管理员账号类型
    public static final Integer TYPE_ADMIN = 0;
    //普通用户账号类型
    public static final Integer TYPE_GENERAL = 1;

    //登录用户名称
    private String username;
    //密码
    private String password;
    //明文密码
    private String plainPassword;
    //salt
    private String salt;
    //用户姓名
    private String fullname;
    //类型
    private Integer type;
    //年龄
    private Date birthDate;
    //性别
    private String sex;
    //联系地址
    private String address;
    //电子邮箱
    private String email;
    //手机
    private String mobile;
    //座机
    private String phone;
    private String qq;
    private String weixin;

    //备用字段
    private String strField1;
    private String strField2;
    private String strField3;
    private String strField4;
    private String strField5;
    private Integer intField1;
    private Integer intField2;
    private Date dateField1;
    private Date dateField2;
    private Date timestampField1;
    private Date timestampField2;

    //是否可用
    private String enabled;
    //所属部门
    private Org org;
    //角色列表（多对多关联）
    private List<Role> roles = new ArrayList<>();
    //权限列表（多对多关联）
    private List<Authority> authorities = new ArrayList<>();

    public User() {

    }

    @Column(name = "username", unique = true, nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "fullname", length = 100)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Column(name = "email", length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "address", length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "enabled")
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SEC_ROLE_USER", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SEC_USER_AUTHORITY", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID")})
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @ManyToOne
    @JoinColumn(name = "org", nullable = true)
    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Transient
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "mobile", length = 15)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "phone", length = 15)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "qq", length = 15)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(name = "weixin", length = 20)
    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    @Column(name = "str_field1", length = 50)
    public String getStrField1() {
        return strField1;
    }

    public void setStrField1(String strField1) {
        this.strField1 = strField1;
    }

    @Column(name = "str_field2", length = 50)
    public String getStrField2() {
        return strField2;
    }

    public void setStrField2(String strField2) {
        this.strField2 = strField2;
    }

    @Column(name = "str_field3", length = 50)
    public String getStrField3() {
        return strField3;
    }

    public void setStrField3(String strField3) {
        this.strField3 = strField3;
    }

    @Column(name = "str_field4", length = 100)
    public String getStrField4() {
        return strField4;
    }

    public void setStrField4(String strField4) {
        this.strField4 = strField4;
    }

    @Column(name = "str_field5", length = 100)
    public String getStrField5() {
        return strField5;
    }

    public void setStrField5(String strField5) {
        this.strField5 = strField5;
    }

    @Column(name = "int_field1")
    public Integer getIntField1() {
        return intField1;
    }

    public void setIntField1(Integer intField1) {
        this.intField1 = intField1;
    }

    @Column(name = "int_field2")
    public Integer getIntField2() {
        return intField2;
    }

    public void setIntField2(Integer intField2) {
        this.intField2 = intField2;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_field1")
    public Date getDateField1() {
        return dateField1;
    }

    public void setDateField1(Date dateField1) {
        this.dateField1 = dateField1;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_field2")
    public Date getDateField2() {
        return dateField2;
    }

    public void setDateField2(Date dateField2) {
        this.dateField2 = dateField2;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp_field1")
    public Date getTimestampField1() {
        return timestampField1;
    }

    public void setTimestampField1(Date timestampField1) {
        this.timestampField1 = timestampField1;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp_field2")
    public Date getTimestampField2() {
        return timestampField2;
    }

    public void setTimestampField2(Date timestampField2) {
        this.timestampField2 = timestampField2;
    }
}
