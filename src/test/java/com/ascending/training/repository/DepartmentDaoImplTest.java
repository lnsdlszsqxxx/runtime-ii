package com.ascending.training.repository;

import com.ascending.training.model.Department;
//import com.ascending.training.repository.DepartmentDaoImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DepartmentDaoImplTest {

    private DepartmentDaoImpl departmentDaoTemp;
    @Autowired
    private Logger logger;

    @Before
    public void setup(){
        departmentDaoTemp = new DepartmentDaoImpl();

        //seed data
        Department department = new Department();
        department.setName("MS");
        department.setDescription("Mathematical Sciences");
        department.setLocation("Exploring Hall");
        departmentDaoTemp.save(department);

    }

    @After
    public void teardown(){
        Assert.assertTrue( departmentDaoTemp.deleteDepartmentByNameAndLocation("MS","Exploring Hall") );
    }

    @Test
    public  void updateTest(){
        Department department = departmentDaoTemp.getDepartmentByName("MS");
        department.setDescription("Miss you");
        departmentDaoTemp.update(department);

        Department department1 = departmentDaoTemp.getDepartmentByName("MS");
        Assert.assertEquals(department1.getDescription(),"Miss you");
    }

    @Test
    public void getDepartmentsTest(){
        List<Department> departments = departmentDaoTemp.getDepartments();
        System.out.println(departments);
    }


    @Test
    public void getDepartmentByNameTest(){
        Department department = departmentDaoTemp.getDepartmentByName("MS");

        System.out.println(department.getDescription());
        Assert.assertEquals(department.getDescription(), "Mathematical Sciences");
    }

    @Test
    public void getDepartmentStudentsAccountsByNameTest(){
        Department department = departmentDaoTemp.getDepartmentStudentsAccountsByDeptName("CS");

        logger.info(department.toString());
        logger.info(department.getStudents().toString());
        logger.info(department.getStudents().stream().findFirst().get().getAccounts().toString());
        Assert.assertEquals(department.getDescription(), "Computer Science");
    }

}
