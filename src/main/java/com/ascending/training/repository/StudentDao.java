package com.ascending.training.repository;

import com.ascending.training.model.Student;

import java.util.List;

public interface StudentDao {

    boolean save(Student student);
    boolean deleteStudentByName(String studentName);
    boolean update(Student student);
    List<Student> getStudents();
    Student getStudentByName(String studentName);

}
