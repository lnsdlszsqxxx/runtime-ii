package com.ascending.training.service;

import com.ascending.training.model.Student;
import com.ascending.training.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    boolean save(Student student){return studentDao.save(student);}
    boolean deleteStudentByName(String studentName){return studentDao.deleteStudentByName(studentName);}
    boolean update(Student student){return studentDao.update(student);}
    List<Student> getStudents(){return studentDao.getStudents();}
    Student getStudentByName(String studentName){return studentDao.getStudentByName(studentName);}

}
