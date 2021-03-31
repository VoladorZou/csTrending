package com.example.demo.dgut.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * collection
 * @author 
 */
@Data
public class Collection implements Serializable {
    /**
     * 收藏文章表主键
     */
    private Integer collectionid;

    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 文章ID
     */
    private Integer articleid;

    /**
     * 收藏文章日期
     */
    private Date collecttime;

    private static final long serialVersionUID = 1L;
}