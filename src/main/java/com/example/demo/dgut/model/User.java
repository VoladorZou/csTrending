package com.example.demo.dgut.model;

import java.io.Serializable;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
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

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户性别
     */
    private String usergender;

    /**
     * 用户头像
     */
    private String userimage;

    /**
     * 删除状态
     */
    private Boolean isdeleted;

    /**
     * 封禁状态
     */
    private Boolean isbanned;

    /**
     * 是否为管理员
     */
    private Boolean isadmin;

    private static final long serialVersionUID = 1L;
}