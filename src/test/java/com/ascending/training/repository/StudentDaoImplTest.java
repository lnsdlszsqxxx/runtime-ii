package com.ascending.training.repository;

import com.ascending.training.model.Account;
import com.ascending.training.model.Department;
import com.ascending.training.model.Student;
import org.junit.*;

import java.util.List;

public class StudentDaoImplTest {

    StudentDaoImpl studentDaoTemp = new StudentDaoImpl();
    DepartmentDaoImpl departmentDaoTemp = new DepartmentDaoImpl();

    @Before
    public void setup(){
        Department department = new Department();
        Student student = new Student();

        student.setAddress("test address");
        student.setEmail("szhang@gmu.edu");
        student.setStName("szhang");
        student.setFirstName("San");
        student.setLastName("Zhang");

        department = departmentDaoTemp.getDepartmentByName("AOES");

//        department.setLocation("GMU campus");
//        department.setName("LS");
//        department.setDescription("Law School");
//        departmentDaoTemp.save(department);

        student.setDepartment(department);

        studentDaoTemp.save(student);
    }

    @After
    public void teardown(){
        Assert.assertTrue( studentDaoTemp.deleteStudentByName("szhang") );
 //       Assert.assertTrue(departmentDaoTemp.delete("LS","GMU campus"));
    }

    @Test
    public void updateTest(){

        Student student = studentDaoTemp.getStudentByName("szhang");

        student.setAddress("1269 Woodbridge");
        studentDaoTemp.update(student);
        Student student1 = studentDaoTemp.getStudentByName("szhang");

        Assert.assertEquals(student1.getAddress(), "1269 Woodbridge");
    }

    @Test
    public void getStudents(){

        List<Student> students = studentDaoTemp.getStudents();
        System.out.println(students.toString());
        Assert.assertEquals(students.size(),7);

    }

    @Test
    public void getStudentByName(){

        Student student = studentDaoTemp.getStudentByName("szhang");
        Assert.assertEquals(student.getEmail(), "szhang@gmu.edu");
    }

}


