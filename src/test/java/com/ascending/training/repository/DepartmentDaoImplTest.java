package com.ascending.training.repository;

import com.ascending.training.model.Department;
//import com.ascending.training.repository.DepartmentDaoImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DepartmentDaoImplTest {

    private DepartmentDaoImpl departmentDaoTemp;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        Assert.assertTrue( departmentDaoTemp.delete("MS","Exploring Hall") );
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
//        Department department = DepartmentDaoImpl.getDepartmentByName("CS");
        System.out.println(department.getDescription()); //left join
        Assert.assertEquals(department.getDescription(), "Mathematical Sciences");
    }

}
