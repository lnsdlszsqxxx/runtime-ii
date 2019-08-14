package com.ascending.training.repository;

import com.ascending.training.model.Account;
import com.ascending.training.util.HibernateUtil;
import com.github.fluent.hibernate.H;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SeekableByteChannel;
import java.util.List;


public class AccountDaoImpl implements AccountDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(Account account){

        boolean isSuccess=true;
        Transaction transaction = null;

        try ( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            isSuccess = false;
            if(transaction != null) transaction.rollback();
        }

        if(isSuccess) logger.info("save done!");

        return isSuccess;
    }




    @Override
    public boolean update(Account account){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(account);
            transaction.commit();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            isSuccess = false;
            if(transaction!=null) transaction.rollback();

        }

        if(isSuccess) logger.info(String.format( "update is done! New record is %s", account.toString()) );

        return  isSuccess;
    }

    @Override
    public boolean delete(String accountType){

        String hql = "DELETE Account WHERE account_type = :accountTypePlaceHolder";
        Transaction transaction = null;
        int deleteCount = 0;


        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Query<Account>  query = session.createQuery(hql);
            query.setParameter("accountTypePlaceHolder", accountType);

            transaction = session.beginTransaction();
            deleteCount = query.executeUpdate();
            transaction.commit();

        }
        catch (Exception e){
            if(transaction!=null) transaction.rollback();
            logger.info(e.getMessage());
        }

        logger.info(String.format("Account %s was deleted", accountType));

        return deleteCount>=1 ? true:false;

    }


    @Override
    public List<Account> getAccounts(){

        String hql = "FROM Account";

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public Account getAccountByName(String accountName){

        if(accountName == null) return null;

        String hql = "FROM Account WHERE lower(account_type) = :accountNamePlaceHolder";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            query.setParameter("accountNamePlaceHolder", accountName.toLowerCase());

            return query.uniqueResult();
        }

    }

}
