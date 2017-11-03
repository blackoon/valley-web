package com.hylanda.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hylanda.entity.UUser;

@Repository
public class UUserRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void add(String key, Long time, UUser uUser) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uUser), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<UUser> uUsers) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uUsers), time, TimeUnit.MINUTES);
    }


    public UUser get(String key) {
        Gson gson = new Gson();
        UUser uUser = null;
        String json = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(json))
            uUser = gson.fromJson(json, UUser.class);
        return uUser;
    }

    public List<UUser> getList(String key) {
        Gson gson = new Gson();
        List<UUser> ts = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(listJson))
            ts = gson.fromJson(listJson, new TypeToken<List<UUser>>(){}.getType());
        return ts;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
