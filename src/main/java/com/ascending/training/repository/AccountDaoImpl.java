package com.ascending.training.repository;

import com.ascending.training.model.Account;
import com.ascending.training.model.Student;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.omg.PortableInterceptor.ACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private Logger logger;

    @Autowired
    private StudentDao studentDao;

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

        if(isSuccess) logger.info(String.format( "update is done! New record is %s", account.toString() ) );

        return  isSuccess;
    }

    @Override
    public boolean deleteAccountByStudentAndType(Student student, String accountType){

        String hql = "DELETE Account WHERE student = :studentPlaceHolder AND accountType = :accountTypePlaceHolder";


        Transaction transaction = null;
        int deleteCount = 0;


        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Query<Account>  query = session.createQuery(hql);
            query.setParameter("studentPlaceHolder", student);
            query.setParameter("accountTypePlaceHolder", accountType);

            transaction = session.beginTransaction();
            deleteCount = query.executeUpdate();
            transaction.commit();

        }
        catch (Exception e){
            if(transaction!=null) transaction.rollback();
            logger.info(e.getMessage());
        }

        if(deleteCount>0) logger.info(String.format("Account %s was deleted", accountType));

        return deleteCount>0 ? true:false;

    }


    @Override
    public List<Account> getAccounts(){

        String hql = "FROM Account as ac " +
                "left join fetch ac.student as st " +
                "left join fetch st.department";

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            return query.list().stream().distinct().collect(Collectors.toList());
        }

    }

    @Override
    public List<Account> getAccountsByStudent(Student student){

        if(student == null) {
            logger.info("student is null!");
            return null;
        }

//        String hql = "FROM Account as ac " +
//                "left join fetch ac.student as st " +
//                "left join fetch st.department " +
//                "WHERE student = :studentPlaceHolder";

        String hql = "FROM Account WHERE student = :studentPlaceHolder";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            query.setParameter("studentPlaceHolder", student);

            return query.list();
        }

    }

    @Override
    public Account getAccountByStudentAndType(Student student, String accountType){

        if(student == null) return null;

        String hql = "FROM Account " +
                "WHERE student = :st AND accountType = :at";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Query<Account> query = session.createQuery(hql);
            query.setParameter("st", student);
            query.setParameter("at",accountType);

            return query.uniqueResult();
        }

    }

}
