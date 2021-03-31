package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.Thump;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ThumpDao {
    int deleteByPrimaryKey(Integer likeid);

    int insert(Thump record);

    int insertSelective(Thump record);

    Thump selectByPrimaryKey(Integer likeid);

    int updateByPrimaryKeySelective(Thump record);

    int updateByPrimaryKey(Thump record);

    // 点赞
    boolean like(@Param("userid") int userid, @Param("articleid") int articleid);

    // 取消点赞
    boolean unlike(@Param("userId") int userId, @Param("articleId") int articleId);

    // 查询点赞的状态
    boolean likeState(@Param("userId") int userId, @Param("articleId") int articleId);
}
