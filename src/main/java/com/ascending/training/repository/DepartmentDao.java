package com.ascending.training.repository;

import com.ascending.training.model.Department;

import java.util.List;

public interface DepartmentDao{
    boolean save(Department department);
    boolean update(Department department);
    boolean deleteDepartmentByNameAndLocation(String deptName, String location);
    List<Department> getDepartments();
    Department getDepartmentByName(String deptName);
    Department getDepartmentStudentsAccountsByDeptName(String deptName);
//    List<Object[]> getDepartmentAndEmployees(String deptName);
//    List<Object[]> getDepartmentAndEmployees(String deptName);
}
