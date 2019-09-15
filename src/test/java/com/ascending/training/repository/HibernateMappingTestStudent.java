package com.ascending.training.repository;

import com.ascending.training.model.Student;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

public class HibernateMappingTestStudent {

    @Autowired
    private Logger logger;


    @Test
    public void Test(){

        String hql = "FROM Employee";
        List<Student> myemployee = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Student> query = session.createQuery(hql);
            myemployee = query.list();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        Assert.assertNotNull(myemployee);

        logger.info(myemployee.get(1).toString());

    }

}
