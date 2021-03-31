package com.example.demo.dgut.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * fellow
 * @author 
 */
@Data
public class Fellow implements Serializable {
    /**
     * 关注用户表ID
     */
    private Integer fellowid;

    /**
     * 关注人
     */
    private Integer userid;

    /**
     * 被关注人
     */
    private Integer uploaderid;

    /**
     * 关注时间
     */
    private Date fellowtime;

    private static final long serialVersionUID = 1L;
}