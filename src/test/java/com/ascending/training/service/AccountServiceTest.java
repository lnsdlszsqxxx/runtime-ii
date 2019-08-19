package com.ascending.training.service;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Account;
import com.ascending.training.model.Student;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AccountServiceTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private StudentService studentService;

    private Account account = new Account();

    @Before
    public void setup(){

        Student student = studentService.getStudentByName("lyu");


        account.setBalance((float) 1.1);
        account.setAccountType("premium saving");
        account.setStudent(student);

        accountService.save(account);

    }

    @After
    public void teardown(){
        Student student = studentService.getStudentByName("lyu");
        Assert.assertTrue( accountService.deleteAccountByStudentAndType(student,"premium saving") );
    }

    @Test
    public void updateTest(){

        Student student = studentService.getStudentByName("lyu");
        account = accountService.getAccountByStudentAndType(student,"premium saving");

        //test getAccountByStudentAndType
        Assert.assertEquals(account.getBalance(), 1.1, 0.00001);

        account.setBalance(2.3F);
        Assert.assertTrue( accountService.update(account) );

        account = accountService.getAccountByStudentAndType(student,"premium saving");
        Assert.assertEquals(account.getBalance(), 2.3F, 0.00001);
    }

    @Test
    public void getAccountsTest(){
        List<Account> accounts = accountService.getAccounts();
        Assert.assertEquals(accounts.size(),13);
    }

    @Test
    public void getAccountsByStudentTest(){
        Student student = studentService.getStudentByName("lyu");
        List<Account> accounts = accountService.getAccountsByStudent(student);
        Assert.assertEquals( accounts.size(),3);
//        accounts.forEach(System.out::println);
        for(Account account: accounts){
            System.out.println(account.getAccountType()+" "+account.getBalance());
        }
    }

}
