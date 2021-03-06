package com.hylanda.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hylanda.entity.URole;

@Repository
public class URoleRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void add(String key, Long time, URole uRole) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uRole), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<URole> uRoles) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uRoles), time, TimeUnit.MINUTES);
    }


    public URole get(String key) {
        Gson gson = new Gson();
        URole uRole = null;
        String json = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(json))
            uRole = gson.fromJson(json, URole.class);
        return uRole;
    }

    public List<URole> getList(String key) {
        Gson gson = new Gson();
        List<URole> ts = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(listJson))
            ts = gson.fromJson(listJson, new TypeToken<List<URole>>(){}.getType());
        return ts;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
