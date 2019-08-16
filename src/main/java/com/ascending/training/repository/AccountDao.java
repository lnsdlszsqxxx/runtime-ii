package com.ascending.training.repository;

import com.ascending.training.model.Account;
import com.ascending.training.model.Student;

import java.util.List;

public interface AccountDao {
    boolean save(Account account);
    boolean deleteAccountByStudentAndType(Student student, String accountType);
    boolean update(Account account);
    List<Account> getAccounts();
    List<Account> getAccountsByStudent(Student student);
    Account getAccountByStudentAndType(Student student, String accountType);
}
