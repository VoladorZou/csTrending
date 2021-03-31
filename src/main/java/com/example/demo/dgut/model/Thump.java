package com.example.demo.dgut.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * thump
 * @author 
 */
@Data
public class Thump implements Serializable {
    /**
     * 点赞ID
     */
    private Integer likeid;

    /**
     * 点赞的用户
     */
    private Integer userid;

    /**
     * 被点赞的文章
     */
    private Integer articleid;

    /**
     * 点赞的时间
     */
    private Date liketime;

    private static final long serialVersionUID = 1L;
}