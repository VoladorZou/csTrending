package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.Comment;
import com.example.demo.dgut.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageDao {
    int deleteByPrimaryKey(Integer messageid);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer messageid);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    // 留言
    boolean sendMessage(Message message);

    // 获取所有留言
    List<Message> getMessageList();

    // 模糊查询（根据查询内容来进行查询结果）
    List<Message> getMessageListByQuery(String Content);
}
