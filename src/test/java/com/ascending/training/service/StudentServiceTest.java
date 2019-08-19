package com.ascending.training.service;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Department;
import com.ascending.training.model.Student;
import com.ascending.training.repository.DepartmentDaoImpl;
import com.ascending.training.repository.StudentDaoImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class StudentServiceTest {

    @Autowired
    StudentService studentService;

    @Autowired
    DepartmentService departmentService;

    @Before
    public void setup(){
        Department department = new Department();
        Student student = new Student();

        student.setAddress("test address");
        student.setEmail("szhang@gmu.edu");
        student.setStName("szhang");
        student.setFirstName("San");
        student.setLastName("Zhang");

        department = departmentService.getDepartmentByName("AOES");

//        department.setLocation("GMU campus");
//        department.setName("LS");
//        department.setDescription("Law School");
//        departmentDaoTemp.save(department);

        student.setDepartment(department);

        studentService.save(student);
    }

    @After
    public void teardown(){
        Assert.assertTrue( studentService.deleteStudentByName("szhang") );
        //       Assert.assertTrue(departmentDaoTemp.delete("LS","GMU campus"));
    }

    @Test
    public void updateTest(){

        Student student = studentService.getStudentByName("szhang");

        student.setAddress("1269 Woodbridge");
        studentService.update(student);
        Student student1 = studentService.getStudentByName("szhang");

        Assert.assertEquals(student1.getAddress(), "1269 Woodbridge");
    }

    @Test
    public void getStudents(){

        List<Student> students = studentService.getStudents();
        System.out.println(students.toString());
        Assert.assertEquals(students.size(),7);

    }

    @Test
    public void getStudentByName(){

        Student student = studentService.getStudentByName("szhang");
        Assert.assertEquals(student.getEmail(), "szhang@gmu.edu");
    }
}
