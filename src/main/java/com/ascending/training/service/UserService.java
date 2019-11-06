package com.ascending.training.service;

import com.ascending.training.model.User;
import com.ascending.training.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public boolean save(User user){
        return userDao.save(user);
    }

    public User getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    }

    public User getUserByUserNameEmail(String userName, String email){
        return userDao.getUserByUserNameEmail(userName, email);
    }

    public User getUserByCredentials(String email,String password){
        return userDao.getUserByCredentials(email,password);
    }

}
