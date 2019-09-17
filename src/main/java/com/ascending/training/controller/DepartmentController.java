package com.ascending.training.controller;

import com.ascending.training.model.Department;
import com.ascending.training.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/departments", "/depts"})   //find the position of controller
public class DepartmentController {

    @Autowired
    private Logger logger;

    @Autowired
    private DepartmentService departmentService; //like front desk offers services.

//    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Department> getDepartments(){
        //return to the front end
        return departmentService.getDepartments();
    }

    //{} means deptName is a variable
    @GetMapping(value = "/{deptName}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Department getDepartmentByName(@PathVariable(name = "deptName") String deptName1){
        return  departmentService.getDepartmentByName(deptName1);
    }


//    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public String creatDepartment(@RequestBody Department department){
//        logger.info("Department: "+department.toString());
//
//        boolean isSuccess =  departmentService.save(department);
//
//        String msg = "The department was created.";
//        if(!isSuccess) msg = "The department was not created.";
//
//        return msg;
//    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String creatDepartments(@RequestBody List<Department> departments){
        logger.info("Department: "+departments.toString());
        boolean isSuccess=true;
        String msg = "The departments were created.";

        for (Department department: departments
             ) {
           isSuccess = departmentService.save(department);
           if(!isSuccess) {msg = "The department was not created."; break;}
        }

        return msg;
    }

    @RequestMapping(value = "/{deptName}/{location}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteDepartmentByNameAndLocation(@PathVariable(name = "deptName") String deptName,
                                                    @PathVariable(name = "location") String location){

        boolean isSuccess = departmentService.deleteDepartmentByNameAndLocation(deptName,location);

        if(isSuccess) return "Deleted successfully!";
        else return "Error when deleting!";
    }


    //"/deleteByDeptNameAndLocation" is not necessary.
    //don't add quotes in the URL for the values.
    @DeleteMapping(value = "/deleteByDeptNameAndLocation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteDepartmentByNameAndLocation2(@RequestParam(value = "deptName", required = true) String deptName1,
                                                     @RequestParam(value = "location", required = true) String location1){
        String msg = "Error when deleting!!";
        logger.info("deptname and location: "+deptName1+" "+location1);
        boolean isSuccess = departmentService.deleteDepartmentByNameAndLocation(deptName1,location1);
        if(isSuccess) msg = "Deleted successfully!!";
        return msg;
    }


    @PatchMapping(value = "/{oldName}")
    public String changeDeptName(@PathVariable (value = "oldName") String oldName1,
                                 @RequestHeader String newName1){
        String msg="Update successfully!";
        boolean isSuccess = true;

        Department department = departmentService.getDepartmentByName(oldName1);
        department.setName(newName1);
        isSuccess = departmentService.update(department);

        if(!isSuccess) msg = "Update Failed!";
        return msg;
    }

}
