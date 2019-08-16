package com.ascending.training.repository;

import com.ascending.training.model.Student;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        AccountDaoImplTest.class,
        DepartmentDaoImplTest.class,
        StudentDaoImplTest.class
})

public class TestSuite {}
