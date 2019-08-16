package com.ascending.training.repository;

import com.ascending.training.model.Account;
import com.ascending.training.model.Student;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AccountDaoImplTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private AccountDaoImpl accountDaoTemp = new AccountDaoImpl();
    private StudentDaoImpl studentDaoTemp = new StudentDaoImpl();
    private Account account = new Account();

    @Before
    public void setup(){

        Student student = studentDaoTemp.getStudentByName("lyu");

        account.setBalance((float) 1.1);
        account.setAccountType("premium saving");
        account.setStudent(student);

        accountDaoTemp.save(account);

    }

    @After
    public void teardown(){
        Student student = studentDaoTemp.getStudentByName("lyu");
        Assert.assertTrue( accountDaoTemp.deleteAccountByStudentAndType(student,"premium saving") );
    }

    @Test
    public void updateTest(){

        Student student = studentDaoTemp.getStudentByName("lyu");
        account = accountDaoTemp.getAccountByStudentAndType(student,"premium saving");

        //test getAccountByStudentAndType
        Assert.assertEquals(account.getBalance(), 1.1, 0.00001);

        account.setBalance(2.3F);
        Assert.assertTrue( accountDaoTemp.update(account) );

        account = accountDaoTemp.getAccountByStudentAndType(student,"premium saving");
        Assert.assertEquals(account.getBalance(), 2.3F, 0.00001);
    }

    @Test
    public void getAccountsTest(){
        List<Account> accounts = accountDaoTemp.getAccounts();
        Assert.assertEquals(accounts.size(),13);
    }

    @Test
    public void getAccountsByStudentTest(){
        Student student = studentDaoTemp.getStudentByName("lyu");
        List<Account> accounts = accountDaoTemp.getAccountsByStudent(student);
        Assert.assertEquals( accounts.size(),3);
//        accounts.forEach(System.out::println);
        for(Account account: accounts){
            System.out.println(account.getAccountType()+" "+account.getBalance());
        }
    }

}
