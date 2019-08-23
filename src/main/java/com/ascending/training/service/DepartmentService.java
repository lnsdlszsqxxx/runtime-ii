package com.ascending.training.service;

import com.ascending.training.model.Department;
import com.ascending.training.repository.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired //DI
    private DepartmentDao departmentDao;

    public boolean save(Department department){return departmentDao.save(department);}
    public boolean update(Department department){return departmentDao.update(department);}
    public boolean deleteDepartmentByNameAndLocation(String deptName, String location){
        return departmentDao.deleteDepartmentByNameAndLocation(deptName,location);
    }
    public List<Department> getDepartments(){return departmentDao.getDepartments();}
    public Department getDepartmentByName(String deptName){return departmentDao.getDepartmentByName(deptName);}
    public Department getDepartmentStudentsAccountsByDeptName(String deptName){
        return departmentDao.getDepartmentStudentsAccountsByDeptName(deptName);
    }

}
