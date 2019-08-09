package com.ascending.training.repository;

import com.ascending.training.model.Department;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class DepartmentDaoImpl implements DepartmentDao{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(Department department){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction!=null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("insert done"));

        return isSuccess;
    }

    @Override
    public boolean update(Department department){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(department);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction!=null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("update done %s", department.toString()));

        return isSuccess;
    }



    @Override
    public boolean delete(String deptName, String location){

        String hql = "DELETE Department where name = :deptName1 and location = :location1"; //place holder,
        int deletedCount = 0;
        Transaction transaction = null;


        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            Query<Department> query = session.createQuery(hql);
            query.setParameter("deptName1", deptName); //replace deptName1 by deptName
            query.setParameter("location1", location); //

            transaction = session.beginTransaction();
            deletedCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("Department %s was deleted", deptName));

        return deletedCount >= 1 ? true:false ;
    }

    @Override
    public List<Department> getDepartments(){

        String hql = "FROM Department";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Department> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public Department getDepartmentByName(String deptName){

        if (deptName == null) return null;

        String hql = "FREOM Department as dept where lower(dept.name) = :name"; //change all letters to lower case;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            Department department = query.uniqueResult(); //
            logger.debug(department.toString());

            return department;
        }

    }



//    departments.forEach(dept -> System.out.println(dept));
}
