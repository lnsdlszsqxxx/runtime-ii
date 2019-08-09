package com.ascending.training.repository;

import com.ascending.training.model.Account;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateMappingTestAccount {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    List<Account> accounts = null;

    @Before
    public void beforeTest() {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Begin test>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Test
    public void mappingTest(){

        String hql = "FROM Department"; //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            accounts = query.list();
        }
        catch (Exception e){
            logger.info(e.getMessage());
        }

        Assert.assertNotNull(accounts);

    }
}
