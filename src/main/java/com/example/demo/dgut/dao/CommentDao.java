package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {
    int deleteByPrimaryKey(Integer commentid);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer commentid);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    // 保存评论
    boolean saveComment(Comment comment);

    // 获取评论
    List<Comment> getComment(int articleid);

    // 删除评论
    boolean deleteComment(@Param("commentid") int commentid, @Param("deleteFlag") boolean deleteFlag);

    // 获取所有评论
    List<Comment> getCommentList();

    // 模糊查询（根据查询内容来进行查询结果）
    List<Comment> getCommentListByQuery(String commentContent);

    // 审核评论
    boolean permitComment(@Param("commentid") int commentid, @Param("isPermited") Boolean isPermited);
}
