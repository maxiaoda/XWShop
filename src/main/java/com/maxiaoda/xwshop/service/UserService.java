package com.maxiaoda.xwshop.service;

import com.maxiaoda.xwshop.dao.UserDao;
import com.maxiaoda.xwshop.generate.User;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUserIfNotExist(String tel) {
        User user = new User();
        user.setTel(tel);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        try{
            userDao.insertUser(user);
        }catch (PersistenceException e){
            return userDao.getUserByTel(tel);
        }
        return user;
    }
}