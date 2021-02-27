package com.example.demo.dgut.service;

import com.example.demo.dgut.model.User;

public interface UserService {

    User login(String userName, String userPassword);

    User loginByPhone(String userName, String userPassword);

    boolean register(User user);

    boolean changePassword(int userId, String newPassword);

    boolean isExistUser(String phoneNum);
}
