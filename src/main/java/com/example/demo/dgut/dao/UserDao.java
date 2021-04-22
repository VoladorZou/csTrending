package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    // 获取全部用户信息
    List<User> getUserList();

    // 模糊查询（根据用户名来进行查询结果）
    List<User> getUserListByUserName(String userName);

    // 用户登录
    User login(@Param("userName") String userName, @Param("userPassword") String userPassword);

    // 用户手机号登录
    User loginByPhone(@Param("phoneNum") String phoneNum, @Param("userPassword") String userPassword);

    // 用户注册
    Boolean register(User user);

    // 修改密码
    Boolean changePassword(@Param("userId") int userId, @Param("userPassword") String userPassword);

    // 重置密码
    Boolean resetPassword(@Param("phoneNum") String phoneNum, @Param("userPassword") String userPassword);

    // 修改用户信息
    Boolean setUserInfo(User user);

    // 审核文章
    boolean permitUser(@Param("userId") int userId, @Param("isPermited") Boolean isPermited);

    // 删除用户信息
    Boolean deleteUserByUserId(@Param("userId")  int userId, @Param("isDeleted") boolean isDeleted);

    // 设置管理员
    Boolean settingAdmin(@Param("userId")  int userId, @Param("isAdmin") boolean isAdmin);

    // 根据手机号判断用户是否存在
    User isExistUser(String phoneNum);

    // 根据用户ID来获取用户信息
    User getUserInfoDao(int userId);

    // 上传用户头像
    Boolean upLoadUserImageDao(@Param("userId") int userId, @Param("userImage") String userImage);
}
