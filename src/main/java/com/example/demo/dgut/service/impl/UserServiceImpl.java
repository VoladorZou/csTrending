package com.example.demo.dgut.service.impl;

import com.example.demo.dgut.dao.UserDao;
import com.example.demo.dgut.model.User;
import com.example.demo.dgut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String userName, String userPassword) {
        try {
            User userDB = userDao.login(userName, userPassword);
            return userDB;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User loginByPhone(String phoneNum, String userPassword) {
        try {
            User userDB = userDao.loginByPhone(phoneNum, userPassword);
            return userDB;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User checkUserInfo(int userId) {
        try {
            User userDB = userDao.getUserInfoDao(userId);
            return userDB;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean register(User user) {
        try {
            userDao.register(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changePassword(int userId, String newPassword) {
        try {
            userDao.changePassword(userId,newPassword);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean resetPassword(String phoneNum, String newPassword) {
        try {
            userDao.resetPassword(phoneNum,newPassword);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isExistUser(String phoneNum) {
        try {
            if(userDao.isExistUser(phoneNum)!=null){
                return true;
            }else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean upLoadUserImage(int userId, String userImage) {
        try {
            userDao.upLoadUserImageDao(userId,userImage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getUserList() {
        try {
            List<User> userList;
            userList = userDao.getUserList();
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
