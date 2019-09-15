package com.ascending.training.repository;

import com.ascending.training.model.User;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    Logger logger;

    @Override
    public boolean save(User user){
        Transaction transaction=null;
        boolean isSuccess=true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if(transaction!=null) transaction.rollback();
            logger.error(e.getMessage(),e);
            logger.error("Save failed! Data rolled back.");
        }

        if(isSuccess) logger.info("Saved successfully! "+user.toString());

        return isSuccess;

    }

    @Override
    public User getUserByEmail(String email){
        String hql = "FROM User as u WHERE lower(u.email) = :emailPH";

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("emailPH", email.toLowerCase());
            return query.uniqueResult();
        }

    }

    @Override
    public User getUserByCredentials(String email, String password){
        String hql = "FROM User as u WHERE lower(u.email) = :emailPH AND u.password = :passwordPH";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("emailPH", email.toLowerCase());
            query.setParameter("passwordPH", password);
            return query.uniqueResult();
        }
    }

}
