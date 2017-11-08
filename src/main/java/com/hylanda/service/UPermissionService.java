package com.hylanda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hylanda.entity.UPermission;
import com.hylanda.model.UPermissionQo;
import com.hylanda.redis.UPermissionRedis;
import com.hylanda.repository.UPermissionRepository;


@Service
public class UPermissionService {
    @Autowired
    private UPermissionRepository uPermissionRepository;
    @Autowired
    private UPermissionRedis uPermissionRedis;
    private static final String keyHead = "mysql:get:uPermission:";

    public UPermission findById(Long id) {
        UPermission uPermission = uPermissionRedis.get(keyHead + id);
        if(uPermission == null){
            uPermission = uPermissionRepository.findOne(id);
            if(uPermission != null)
                uPermissionRedis.add(keyHead + id, 30L, uPermission);
        }
        return uPermission;
    }

    public UPermission create(UPermission uPermission) {
        UPermission newUPermission = uPermissionRepository.save(uPermission);
        if(newUPermission != null)
            uPermissionRedis.add(keyHead + newUPermission.getId(), 30L, newUPermission);
        return newUPermission;
    }

    public UPermission update(UPermission uPermission) {
        if(uPermission != null) {
            uPermissionRedis.delete(keyHead + uPermission.getId());
            uPermissionRedis.add(keyHead + uPermission.getId(), 30L, uPermission);
        }
        return uPermissionRepository.save(uPermission);
    }

    public void delete(Long id) {
        uPermissionRedis.delete(keyHead + id);
        uPermissionRepository.delete(id);
    }

    public Page<UPermission> findPage(UPermissionQo uPermissionQo){
       Pageable pageable = new PageRequest(uPermissionQo.getPage(), uPermissionQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
       return uPermissionRepository.findAll(pageable);
    }

	public List<UPermission> findAll() {
		List<UPermission> permissions = uPermissionRedis.getList("mysql:findAll:uPermission");
        if(permissions == null) {
        	permissions = uPermissionRepository.findAll();
            if(permissions != null)
            	uPermissionRedis.add("mysql:findAll:uPermission", 5L, permissions);
        }
        return permissions;
	}

}
