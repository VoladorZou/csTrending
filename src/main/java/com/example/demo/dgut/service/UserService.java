package com.example.demo.dgut.service;

import com.example.demo.dgut.model.User;

import java.util.List;

public interface UserService {

    User login(String userName, String userPassword);

    User loginByPhone(String userName, String userPassword);

    User checkUserInfo(int userId);

    boolean register(User user);

    boolean changePassword(int userId, String newPassword);

    boolean resetPassword(String phoneNum, String newPassword);

    boolean isExistUser(String phoneNum);

    boolean upLoadUserImage(int userId, String userImage);

    // 获取全部用户信息
    List<User> getUserList();
}
