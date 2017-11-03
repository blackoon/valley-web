package com.hylanda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.hylanda.entity.UUserRole;
import com.hylanda.model.UUserRoleQo;
import com.hylanda.redis.UUserRoleRedis;
import com.hylanda.repository.UUserRoleRepository;


@Service
public class UUserRoleService {
    @Autowired
    private UUserRoleRepository uUserRoleRepository;
    @Autowired
    private UUserRoleRedis uUserRoleRedis;
    private static final String keyHead = "mysql:get:uUserRole:";

    public UUserRole findById(Long id) {
        UUserRole uUserRole = uUserRoleRedis.get(keyHead + id);
        if(uUserRole == null){
            uUserRole = uUserRoleRepository.findOne(id);
            if(uUserRole != null)
                uUserRoleRedis.add(keyHead + id, 30L, uUserRole);
        }
        return uUserRole;
    }

    public UUserRole create(UUserRole uUserRole) {
        UUserRole newUUserRole = uUserRoleRepository.save(uUserRole);
        if(newUUserRole != null)
            uUserRoleRedis.add(keyHead + newUUserRole.getId(), 30L, newUUserRole);
        return newUUserRole;
    }

    public UUserRole update(UUserRole uUserRole) {
        if(uUserRole != null) {
            uUserRoleRedis.delete(keyHead + uUserRole.getId());
            uUserRoleRedis.add(keyHead + uUserRole.getId(), 30L, uUserRole);
        }
        return uUserRoleRepository.save(uUserRole);
    }

    public void delete(Long id) {
        uUserRoleRedis.delete(keyHead + id);
        uUserRoleRepository.delete(id);
    }

    public Page<UUserRole> findPage(UUserRoleQo uUserRoleQo){
       Pageable pageable = new PageRequest(uUserRoleQo.getPage(), uUserRoleQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
       return uUserRoleRepository.findAll(pageable);
    }
}
