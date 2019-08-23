package com.ascending.training.controller;

import com.ascending.training.model.Student;
import com.ascending.training.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = {"/students", "/sts"})
public class StudentController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Student> getStudents(){
        return  studentService.getStudents();
    }

    @GetMapping(value = "/{stName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Student getStudent(@PathVariable(name = "stName") String stName){
        return studentService.getStudentByName(stName);
    }

    @PostMapping(value = "",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createStudent(@RequestBody Student student){
        logger.info(student.toString());
        boolean isSuccess = studentService.save(student);
        return isSuccess?"Students created successfully":"Creation failed";
    }

    @PostMapping(value = "/createStudents",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createStudents(@RequestBody List<Student> students){
        boolean isSuccess=true;

        for (Student student:students
             ) {
            isSuccess = studentService.save(student);
            if(!isSuccess) break;
        }

        return isSuccess?"Students created successfully":"Creation failed";
    }

    @DeleteMapping(value = "/{stName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteStudent(@PathVariable(name = "stName") String stName1){
        boolean isSuccess = studentService.deleteStudentByName(stName1);

        return isSuccess?"Deleted Successfully!":"Delete Failure";
    }




}
