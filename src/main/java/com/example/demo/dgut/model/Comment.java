package com.example.demo.dgut.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * comment
 * @author 
 */
@Data
public class Comment implements Serializable {
    /**
     * 评论ID
     */
    private Integer commentid;

    /**
     * 评论内容
     */
    private String commentcontent;

    /**
     * 评论时间
     */
    private Date commenttime;

    /**
     * 发表评论的用户
     */
    private Integer userid;

    /**
     * 被评论的文章
     */
    private Integer articleid;

    /**
     * 审核状态
     */
    private Boolean ispermited;

    /**
     * 删除状态
     */
    private Boolean isdeleted;

    private static final long serialVersionUID = 1L;
}