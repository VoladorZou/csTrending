package com.example.demo.dgut.model;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * user
 * @author
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String userpassword;

    /**
     * 用户手机号
     */
    private String phonenum;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsergender() {
        return usergender;
    }

    public void setUsergender(String usergender) {
        this.usergender = usergender;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户性别
     */
    private String usergender;

    private static final long serialVersionUID = 1L;
}
