package com.ascending.training.jdbc;

import com.ascending.training.model.Department;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DepartmentDaoTest {


    private DepartmentDao departmentDao;
    @Autowired
    private Logger logger;


    @Before
    public void init(){
        departmentDao = new DepartmentDao();
    }

    @After
    public void cleanup(){

        System.out.println("After cleanup.");

    }

//    @Ignore
    @Test
    public void getDepartmentsTest(){

        List<Department> departments = departmentDao.getDepartments();
//        int expectedNumOfDept = 5;
        Long i=0L;

        for (Department department: departments){

            i++;

            System.out.println(department.getId());

            Assert.assertEquals(i, department.getId());

        }

    }


}
