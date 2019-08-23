package com.ascending.training.service;

import com.ascending.training.model.Account;
import com.ascending.training.model.Student;
import com.ascending.training.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public boolean save(Account account){return accountDao.save(account);}
    public boolean deleteAccountByStudentAndType(Student student, String accountType){return accountDao.deleteAccountByStudentAndType(student,accountType);}
    public boolean update(Account account){return accountDao.update(account);}
    public List<Account> getAccounts(){return accountDao.getAccounts();}
    public List<Account> getAccountsByStudent(Student student){return accountDao.getAccountsByStudent(student);}
    public Account getAccountByStudentAndType(Student student, String accountType){return accountDao.getAccountByStudentAndType(student,accountType);}
}
