package com.ascending.training.controller;

import com.ascending.training.model.Account;
import com.ascending.training.model.Student;
import com.ascending.training.service.AccountService;
import com.ascending.training.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/accounts","/accs"})
public class AccountController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;

    @Autowired
    StudentService studentService;

//    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Account> getAccounts(){
//        return accountService.getAccounts();
//    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping(value = "/{stName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAccountsByStName(@PathVariable(name = "stName") String stName){
        Student student = studentService.getStudentByName(stName);
        System.out.println(student.toString());
        return accountService.getAccountsByStudent(student);
    }

    @PatchMapping(value = "/withdraw/{stName}/{accountType}/{amount}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String withdraw(@PathVariable(name = "stName", required = true) String stName,
                           @PathVariable(name = "accountType", required = true) String accountType,
                           @PathVariable(name = "amount", required = true) String amount){
        boolean isSuccess=true;
        Student student = studentService.getStudentByName(stName);
        Account account = accountService.getAccountByStudentAndType(student, accountType);
        float oldBalance = account.getBalance();
        float newBalance = oldBalance - Float.valueOf(amount);
        account.setBalance(newBalance);

        if(newBalance<0) return "Current Balance is $" + oldBalance + ". Cannot withdraw $"+amount+".";

        isSuccess = accountService.update(account);

        String msg = stName + " withdrew from "+accountType+ " account for "+ amount + " dollars.";

        if(isSuccess) msg = msg + " Successfully!"+" New balance is "+newBalance;
        else msg = msg + " Failed!";

        return msg;
    }

    @PatchMapping(value = "/deposit/{stName}/{accountType}/{amount}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deposit(@PathVariable(name = "stName", required = true) String stName,
                          @PathVariable(name = "accountType", required = true) String accountType,
                          @PathVariable(name = "amount", required = true) String amount){
        boolean isSuccess=true;
        Student student = studentService.getStudentByName(stName);
        Account account = accountService.getAccountByStudentAndType(student, accountType);

        float oldBalance = account.getBalance();
        float newBalance = oldBalance + Float.valueOf(amount);
        account.setBalance(newBalance);
        isSuccess = accountService.update(account);

        String msg = stName + " saved "+ amount + " dollars to "+accountType+ " account";

        if(isSuccess) msg = msg + " Successfully!"+" New balance is "+newBalance;
        else msg = msg + " Failed!";

        return msg;
    }

}
