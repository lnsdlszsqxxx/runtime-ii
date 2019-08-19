package com.ascending.training.repository;

import com.ascending.training.model.Department;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sun.jvm.hotspot.utilities.Assert;

import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao{

    Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public boolean deleteDepartmentByNameAndLocation(String deptName, String location){

        String hql = "DELETE Department where name = :deptName1 and location = :location1"; //place holder,
        int deletedCount = 0;
        Transaction transaction = null;



        try(Session session = HibernateUtil.getSessionFactory().openSession()){

//            Department department = getDepartmentByName(deptName);
//            session.delete(department);

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

        if(deletedCount>0) logger.info(String.format("Department %s was deleted", deptName));

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

        String hql = "FROM Department as dept where lower(dept.name) = :name"; //change all letters to lower case;


        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            return query.uniqueResult();
        }

    }


    @Override
    public Department getDepartmentStudentsAccountsByDeptName(String deptName){

        if (deptName == null) return null;

        //To use this hql, you must use Set in Model class, you cannot use List
        String hql = "FROM Department as dept " +
                "left join fetch dept.students as st " +
                "left join fetch st.accounts " +
                "where lower(dept.name) = :name"; //change all letters to lower case;


        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            return query.uniqueResult();
        }

    }



//    departments.forEach(dept -> System.out.println(dept));
}
