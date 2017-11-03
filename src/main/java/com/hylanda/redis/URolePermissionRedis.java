package com.hylanda.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hylanda.entity.URolePermission;

@Repository
public class URolePermissionRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void add(String key, Long time, URolePermission uRolePermission) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uRolePermission), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<URolePermission> uRolePermissions) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(uRolePermissions), time, TimeUnit.MINUTES);
    }


    public URolePermission get(String key) {
        Gson gson = new Gson();
        URolePermission uRolePermission = null;
        String json = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(json))
            uRolePermission = gson.fromJson(json, URolePermission.class);
        return uRolePermission;
    }

    public List<URolePermission> getList(String key) {
        Gson gson = new Gson();
        List<URolePermission> ts = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(listJson))
            ts = gson.fromJson(listJson, new TypeToken<List<URolePermission>>(){}.getType());
        return ts;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
