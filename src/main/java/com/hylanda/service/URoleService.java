package com.hylanda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.hylanda.entity.URole;
import com.hylanda.model.URoleQo;
import com.hylanda.redis.URoleRedis;
import com.hylanda.repository.URoleRepository;


@Service
public class URoleService {
    @Autowired
    private URoleRepository uRoleRepository;
    @Autowired
    private URoleRedis uRoleRedis;
    private static final String keyHead = "mysql:get:uRole:";

    public URole findById(Long id) {
        URole uRole = uRoleRedis.get(keyHead + id);
        if(uRole == null){
            uRole = uRoleRepository.findOne(id);
            if(uRole != null)
                uRoleRedis.add(keyHead + id, 30L, uRole);
        }
        return uRole;
    }

    public URole create(URole uRole) {
        URole newURole = uRoleRepository.save(uRole);
        if(newURole != null)
            uRoleRedis.add(keyHead + newURole.getId(), 30L, newURole);
        return newURole;
    }

    public URole update(URole uRole) {
        if(uRole != null) {
            uRoleRedis.delete(keyHead + uRole.getId());
            uRoleRedis.add(keyHead + uRole.getId(), 30L, uRole);
        }
        return uRoleRepository.save(uRole);
    }

    public void delete(Long id) {
        uRoleRedis.delete(keyHead + id);
        uRoleRepository.delete(id);
    }

    public Page<URole> findPage(URoleQo uRoleQo){
       Pageable pageable = new PageRequest(uRoleQo.getPage(), uRoleQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
       return uRoleRepository.findAll(pageable);
    }
}
