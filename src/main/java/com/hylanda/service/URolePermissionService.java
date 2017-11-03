package com.hylanda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.hylanda.entity.URolePermission;
import com.hylanda.model.URolePermissionQo;
import com.hylanda.redis.URolePermissionRedis;
import com.hylanda.repository.URolePermissionRepository;


@Service
public class URolePermissionService {
    @Autowired
    private URolePermissionRepository uRolePermissionRepository;
    @Autowired
    private URolePermissionRedis uRolePermissionRedis;
    private static final String keyHead = "mysql:get:uRolePermission:";

    public URolePermission findById(Long id) {
        URolePermission uRolePermission = uRolePermissionRedis.get(keyHead + id);
        if(uRolePermission == null){
            uRolePermission = uRolePermissionRepository.findOne(id);
            if(uRolePermission != null)
                uRolePermissionRedis.add(keyHead + id, 30L, uRolePermission);
        }
        return uRolePermission;
    }

    public URolePermission create(URolePermission uRolePermission) {
        URolePermission newURolePermission = uRolePermissionRepository.save(uRolePermission);
        if(newURolePermission != null)
            uRolePermissionRedis.add(keyHead + newURolePermission.getId(), 30L, newURolePermission);
        return newURolePermission;
    }

    public URolePermission update(URolePermission uRolePermission) {
        if(uRolePermission != null) {
            uRolePermissionRedis.delete(keyHead + uRolePermission.getId());
            uRolePermissionRedis.add(keyHead + uRolePermission.getId(), 30L, uRolePermission);
        }
        return uRolePermissionRepository.save(uRolePermission);
    }

    public void delete(Long id) {
        uRolePermissionRedis.delete(keyHead + id);
        uRolePermissionRepository.delete(id);
    }

    public Page<URolePermission> findPage(URolePermissionQo uRolePermissionQo){
       Pageable pageable = new PageRequest(uRolePermissionQo.getPage(), uRolePermissionQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
       return uRolePermissionRepository.findAll(pageable);
    }
}
