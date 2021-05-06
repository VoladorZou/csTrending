package com.example.demo.dgut.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * message
 * @author 
 */
@Data
public class Message implements Serializable {
    /**
     * 留言ID
     */
    private Integer messageid;

    /**
     * 留言的用户ID
     */
    private Integer userid;

    /**
     * 留言人的联系方式
     */
    private String contact;

    /**
     * 留言
     */
    private String context;

    /**
     * 留言时间
     */
    private Date messagetime;

    private static final long serialVersionUID = 1L;
}