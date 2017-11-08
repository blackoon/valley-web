package com.hylanda.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hylanda.entity.Account;

@Repository
public class AccountRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void add(String key, Long time, Account account) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(account), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<Account> accounts) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(accounts), time, TimeUnit.MINUTES);
    }


    public Account get(String key) {
        Gson gson = new Gson();
        Account account = null;
        String json = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(json))
            account = gson.fromJson(json, Account.class);
        return account;
    }

    public List<Account> getList(String key) {
        Gson gson = new Gson();
        List<Account> ts = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(listJson))
            ts = gson.fromJson(listJson, new TypeToken<List<Account>>(){}.getType());
        return ts;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
