package com.ascending.training.controller;

import com.ascending.training.model.Department;
import com.ascending.training.model.Student;

import java.util.List;

public class Wrapper {

    private Department department;
    private List<Student> students;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudent(List<Student> students) {
        this.students = students;
    }
}
