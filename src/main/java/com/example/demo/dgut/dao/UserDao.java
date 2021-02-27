package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    // 用户登录
    User login(@Param("userName") String userName, @Param("userPassword") String userPassword);

    // 用户手机号登录
    User loginByPhone(@Param("phoneNum") String phoneNum, @Param("userPassword") String userPassword);

    // 用户注册
    Boolean register(User user);

    // 修改密码
    Boolean changePassword(@Param("userId") int userId, @Param("userPassword") String userPassword);

    // 根据手机号判断用户是否存在
    User isExistUser(String phoneNum);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
