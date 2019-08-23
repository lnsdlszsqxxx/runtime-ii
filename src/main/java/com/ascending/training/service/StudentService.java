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

    public boolean save(Student student){return studentDao.save(student);}
    public boolean deleteStudentByName(String studentName){return studentDao.deleteStudentByName(studentName);}
    public boolean update(Student student){return studentDao.update(student);}
    public List<Student> getStudents(){return studentDao.getStudents();}
    public Student getStudentByName(String studentName){return studentDao.getStudentByName(studentName);}
}
