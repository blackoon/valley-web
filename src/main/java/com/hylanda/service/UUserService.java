package com.hylanda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hylanda.entity.URole;
import com.hylanda.entity.UUser;
import com.hylanda.model.UUserQo;
import com.hylanda.redis.UUserRedis;
import com.hylanda.repository.UUserRepository;
import com.hylanda.util.MyDES;


@Service
public class UUserService {
    @Autowired
    private UUserRepository uUserRepository;
    @Autowired
    private UUserRedis uUserRedis;
    private static final String keyHead = "mysql:get:uUser:";

    public UUser findById(Long id) {
        UUser uUser = uUserRedis.get(keyHead + id);
        if(uUser == null){
            uUser = uUserRepository.findOne(id);
            if(uUser != null)
                uUserRedis.add(keyHead + id, 30L, uUser);
        }
        return uUser;
    }

    public UUser create(UUser uUser) {
    	uUser.setPswd(MyDES.encryptBasedDes(uUser.getPswd()+uUser.getNickname()));
        UUser newUUser = uUserRepository.save(uUser);
        if(newUUser != null)
            uUserRedis.add(keyHead + newUUser.getId(), 30L, newUUser);
        return newUUser;
    }

    public UUser update(UUser uUser) {
        if(uUser != null) {
            uUserRedis.delete(keyHead + uUser.getId());
            uUserRedis.add(keyHead + uUser.getId(), 30L, uUser);
        }
        return uUserRepository.save(uUser);
    }

    public void delete(Long id) {
        uUserRedis.delete(keyHead + id);
        uUserRepository.delete(id);
    }

    public Page<UUser> findPage(UUserQo uUserQo){
       Pageable pageable = new PageRequest(uUserQo.getPage(), uUserQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
//     PredicateBuilder pb  = new PredicateBuilder();

//     if (!StringUtils.isEmpty(userQo.getName())) {
//         pb.add("name","%" + userQo.getName() + "%", LinkEnum.LIKE);
//     }
//     if (!StringUtils.isEmpty(userQo.getCreatedateStart())) {
//         pb.add("createdate",userQo.getCreatedateStart(), LinkEnum.GE);
//     }
//     if (!StringUtils.isEmpty(userQo.getCreatedateEnd())) {
//         pb.add("createdate",userQo.getCreatedateEnd(), LinkEnum.LE);
//     }
       return uUserRepository.findAll(pageable);
    }
    public UUser findByUsername(String username, String password) {
    	UUser user=uUserRepository.findByNickname(username);
    	if(user!=null&&user.getPswd().equals(password))
    		return user;
		return null;
	}

	public List<URole> findByUserId(Long userId) {
		UUser user=uUserRepository.findOne(userId);
		return user.getRoles();
	}
}
