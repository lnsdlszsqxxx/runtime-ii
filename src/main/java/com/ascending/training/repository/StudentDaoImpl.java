package com.ascending.training.repository;

import com.ascending.training.model.Student;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StudentDaoImpl implements StudentDao {

    Logger logger;

    @Override
    public boolean save(Student student){

        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        }
        catch (Exception e){
            if(transaction != null) transaction.rollback();
            logger.error(e.getMessage());
            isSuccess = false;
        }

        if(isSuccess) logger.info(String.format("save done! %s", student));

        return isSuccess;
    }

    @Override
    public boolean deleteStudentByName(String studentName){
        String hql = "DELETE Student WHERE stName like :namePlaceHolder";
        Transaction transaction = null;
        int deleteCount =0;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Query<Student> query = session.createQuery(hql);
            query.setParameter("namePlaceHolder", studentName+"%");

            transaction = session.beginTransaction();
            deleteCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){

            if(transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if(deleteCount>0) logger.info(String.format("Student %s was deleted!", studentName));
        return deleteCount>0 ? true:false ;

    }

    @Override
    public boolean update(Student student){

        Transaction transaction = null;
        boolean isSuccess= true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            transaction = session.beginTransaction();
            session.saveOrUpdate(student);
            transaction.commit();
        }
        catch (Exception e ){
            if(transaction != null) transaction.rollback();
            isSuccess = false;
            logger.error(e.getMessage());
        }

        if(isSuccess) logger.info(String.format("Update %s is done!", student));

        return isSuccess;


    }

    @Override
    public List<Student> getStudents(){

        String hql = "FROM Student AS st " +
                "left join fetch st.accounts " +
                "left join fetch st.department";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Student> query = session.createQuery(hql);
//            List<Student> students = query.list();
//            return students;

            //no repeated results, method 1
//            List<Student> students = query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//            return students;

            //method 2
            List<Student> students = query.list().stream().distinct().collect(Collectors.toList());
            return students;

//            //method 3
//            Set<Student> set = new HashSet<>(students);
//            List<Student> students1 = new ArrayList<>(set);
//            return students1;

        }
    }

    @Override
    public Student getStudentByName(String studentName){
        String hql = "FROM Student AS st " +
                "left join fetch st.accounts " +
                "left join fetch st.department " +
                "where lower(st.stName) = :namePlaceHolder";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Student> query = session.createQuery(hql);
            query.setParameter("namePlaceHolder", studentName.toLowerCase());

            return query.uniqueResult();
        }
    }

}
