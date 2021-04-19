package com.example.demo.dgut.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * article
 * @author 
 */
@Data
public class Article implements Serializable {
    /**
     * 文章编号
     */
    private Integer articleid;

    /**
     * 用户编号
     */
    private Integer userid;

    /**
     * 文章的md格式
     */
    private String articlemd;

    /**
     * 文章的html格式
     */
    private String articlehtml;

    /**
     * 文章的写入日期
     */
    private Date createtime;

    /**
     * 数值0代表未审核或未通过，数值1代表审核通过
     */
    private Boolean ispermited;

    /**
     * 文章标题
     */
    private String articletitle;

    /**
     * 文章封面图片
     */
    private String articlecover;

    /**
     * 评论
     */
    private String comment;

    /**
     * 标签或分类
     */
    private String tag;

    /**
     * 测试mysql方式存储md
     */
    private String markdown;

    /**
     * 点赞数
     */
    private Integer thumbsup;

    /**
     * 删除状态
     */
    private Boolean isdeleted;

    /**
     * 浏览量
     */
    private Integer views;

    private static final long serialVersionUID = 1L;
}