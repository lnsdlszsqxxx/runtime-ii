package com.ascending.training.repository;

import com.ascending.training.model.Account;

import java.util.List;

public interface AccountDao {
    boolean save(Account account);
    boolean delete(String accountType);
    boolean update(Account account);
    List<Account> getAccounts();
    Account getAccountByName(String accountName);
}
