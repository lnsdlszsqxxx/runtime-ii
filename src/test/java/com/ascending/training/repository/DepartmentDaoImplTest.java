package com.ascending.training.repository;

import com.ascending.training.model.Department;
//import com.ascending.training.repository.DepartmentDaoImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DepartmentDaoImplTest {

    @Before

    @Test
    public void getDepartmentByNameTest(){
        DepartmentDaoImpl temp = new DepartmentDaoImpl();
        Department department = temp.getDepartmentByName("CS");
//        Department department = DepartmentDaoImpl.getDepartmentByName("CS");
        System.out.println(department.getDescription()); //left join
        Assert.assertEquals(department.getDescription(), "Custom Service");
    }

}
