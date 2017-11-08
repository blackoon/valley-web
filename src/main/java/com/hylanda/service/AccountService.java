package com.hylanda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.hylanda.entity.Account;
import com.hylanda.model.AccountQo;
import com.hylanda.redis.AccountRedis;
import com.hylanda.repository.AccountRepository;


@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountRedis accountRedis;
    private static final String keyHead = "mysql:get:account:";

    public Account findById(Long id) {
        Account account = accountRedis.get(keyHead + id);
        if(account == null){
            account = accountRepository.findOne(id);
            if(account != null)
                accountRedis.add(keyHead + id, 30L, account);
        }
        return account;
    }

    public Account create(Account account) {
        Account newAccount = accountRepository.save(account);
        if(newAccount != null)
            accountRedis.add(keyHead + newAccount.getId(), 30L, newAccount);
        return newAccount;
    }

    public Account update(Account account) {
        if(account != null) {
            accountRedis.delete(keyHead + account.getId());
            accountRedis.add(keyHead + account.getId(), 30L, account);
        }
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        accountRedis.delete(keyHead + id);
        accountRepository.delete(id);
    }

    public Page<Account> findPage(AccountQo accountQo){
       Pageable pageable = new PageRequest(accountQo.getPage(), accountQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
       return accountRepository.findAll(pageable);
    }
}
