package com.hylanda.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hylanda.entity.UUserRole;

@Repository
public class UUserRoleRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void add(String key, Long time, UUserRole uUserRole) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uUserRole), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<UUserRole> uUserRoles) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uUserRoles), time, TimeUnit.MINUTES);
    }


    public UUserRole get(String key) {
        Gson gson = new Gson();
        UUserRole uUserRole = null;
        String json = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(json))
            uUserRole = gson.fromJson(json, UUserRole.class);
        return uUserRole;
    }

    public List<UUserRole> getList(String key) {
        Gson gson = new Gson();
        List<UUserRole> ts = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(listJson))
            ts = gson.fromJson(listJson, new TypeToken<List<UUserRole>>(){}.getType());
        return ts;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
