package com.hylanda.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hylanda.entity.UPermission;

@Repository
public class UPermissionRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void add(String key, Long time, UPermission uPermission) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uPermission), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<UPermission> uPermissions) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uPermissions), time, TimeUnit.MINUTES);
    }


    public UPermission get(String key) {
        Gson gson = new Gson();
        UPermission uPermission = null;
        String json = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(json))
            uPermission = gson.fromJson(json, UPermission.class);
        return uPermission;
    }

    public List<UPermission> getList(String key) {
        Gson gson = new Gson();
        List<UPermission> ts = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(listJson))
            ts = gson.fromJson(listJson, new TypeToken<List<UPermission>>(){}.getType());
        return ts;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
