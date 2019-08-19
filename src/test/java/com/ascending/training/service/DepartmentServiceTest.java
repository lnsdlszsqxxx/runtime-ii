package com.ascending.training.service;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Department;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class DepartmentServiceTest {

        @Autowired //so you don't need new DepartmentService() on the right hand side;
        private DepartmentService departmentService;
        private Logger logger = LoggerFactory.getLogger(this.getClass());

        @Before
        public void setup(){
//            departmentService = new DepartmentService();

            //seed data
            Department department = new Department();
            department.setName("MS");
            department.setDescription("Mathematical Sciences");
            department.setLocation("Exploring Hall");
            departmentService.save(department);

        }


        @After
        public void teardown(){
            Assert.assertTrue( departmentService.deleteDepartmentByNameAndLocation("MS","Exploring Hall") );
        }

        @Test
        public  void updateTest(){
            Department department = departmentService.getDepartmentByName("MS");
            department.setDescription("Miss you");
            departmentService.update(department);

            Department department1 = departmentService.getDepartmentByName("MS");
            Assert.assertEquals(department1.getDescription(),"Miss you");
        }

        @Test
        public void getDepartmentsTest(){
            List<Department> departments = departmentService.getDepartments();
            System.out.println(departments);
        }


        @Test
        public void getDepartmentByNameTest(){
            Department department = departmentService.getDepartmentByName("MS");

            System.out.println(department.getDescription());
            Assert.assertEquals(department.getDescription(), "Mathematical Sciences");
        }

        @Test
        public void getDepartmentStudentsAccountsByNameTest(){
            Department department = departmentService.getDepartmentStudentsAccountsByDeptName("CS");

            logger.info(department.toString());
            logger.info(department.getStudents().toString());
            logger.info(department.getStudents().stream().findFirst().get().getAccounts().toString());
            Assert.assertEquals(department.getDescription(), "Computer Science");
        }

}

