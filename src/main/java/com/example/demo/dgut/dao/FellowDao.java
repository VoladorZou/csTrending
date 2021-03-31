package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.Article;
import com.example.demo.dgut.model.Fellow;
import com.example.demo.dgut.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FellowDao {
    int deleteByPrimaryKey(Integer fellowid);

    int insert(Fellow record);

    int insertSelective(Fellow record);

    Fellow selectByPrimaryKey(Integer fellowid);

    int updateByPrimaryKeySelective(Fellow record);

    int updateByPrimaryKey(Fellow record);

    // 添加关注
    Boolean fellowing(@Param("userId") int userId, @Param("uploaderId") int uploaderId);

    // 取消关注
    Boolean unfellow(@Param("userId") int userId, @Param("uploaderId") int uploaderId);

    // 查询关注状态
    Boolean fellowState(@Param("userId") int userId, @Param("uploaderId") int uploaderId);

    // 根据当前登录账号ID获取关注列表
    List<Fellow> getFellowList(int userId);
}
