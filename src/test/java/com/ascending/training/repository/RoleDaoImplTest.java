package com.ascending.training.repository;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class) //So we don't need JUnit
@SpringBootTest(classes = AppInitializer.class)
public class RoleDaoImplTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RoleDaoImpl roleDao;
//    RoleDaoImpl roleDao = new RoleDaoImpl();

    @Before
    public void setup(){
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Test
    public void test(){

        String roleName="user";
        Role role = roleDao.getRoleByName(roleName);
        logger.info(role.toString());
        Assert.assertEquals(role.getName(), roleName);
    }


}
