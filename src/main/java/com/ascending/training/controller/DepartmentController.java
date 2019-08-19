package com.ascending.training.controller;

import com.ascending.training.model.Department;
import com.ascending.training.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/departments"})
public class DepartmentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DepartmentService departmentService; //like front desk offers services.

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Department> getDepartments(){return departmentService.getDepartments();}

}
