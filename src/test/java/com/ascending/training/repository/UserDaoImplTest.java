package com.ascending.training.repository;


import com.ascending.training.model.Role;
import com.ascending.training.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImplTest {


    Logger logger = LoggerFactory.getLogger(this.getClass());
    UserDaoImpl userDao = new UserDaoImpl();
    RoleDaoImpl roleDao = new RoleDaoImpl();

    @Test
    public void saveTest(){

        User user = new User();
        user.setEmail("lyu@gmu.edu");
        user.setFirstName("Liang");
        user.setLastName("Yu");
        user.setName("lyu");
        user.setPassword("123");

        Assert.assertTrue(userDao.save(user));
        User user1 = userDao.getUserByEmail("lyu@gmu.edu");
        Assert.assertEquals(user.getName(),user1.getName());

    }

    @Test
    public void getUserByCredentials(){
        String email = "rhang@ascending.com";
        String password="25f9e794323b453885f5181f1b624d0b";
        User user = userDao.getUserByCredentials(email,password);
        logger.info(user.toString());
        Assert.assertEquals(user.getEmail(),email);
        Assert.assertEquals(user.getPassword(),password);
    }

    @Test
    public void getUserByEmailTest(){
        String email = "rhang@ascending.com";
        User user = userDao.getUserByEmail(email);
        logger.info(user.toString());
        Assert.assertEquals(user.getEmail(),email);
    }
}
