package com.hylanda.controller.rest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hylanda.entity.UUser;

/**
 * @author zhangy
 * @E-mail:blackoon88@gmail.com
 * @qq:846579287
 * @version created at：2017年11月6日 下午2:12:41 note
 */
@RestController
@RequestMapping("/api/users")
public class TestApiController {
	static Map<Long, UUser> UUsers = Collections.synchronizedMap(new HashMap<Long, UUser>());
    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value={""}, method=RequestMethod.GET)
    public List<UUser> getUUserList() {
        List<UUser> r = new ArrayList<UUser>(UUsers.values());
        return r;
    }

    @ApiOperation(value="创建用户", notes="根据UUser对象创建用户")
    @ApiImplicitParam(name = "UUser", value = "用户详细实体UUser", required = true, dataType = "UUser")
    @RequestMapping(value="", method=RequestMethod.POST)
    public String postUUser(@RequestBody UUser UUser) {
        UUsers.put(UUser.getId(), UUser);
        return "success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public UUser getUUser(@PathVariable Long id) {
        return UUsers.get(id);
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的UUser信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "UUser", value = "用户详细实体UUser", required = true, dataType = "UUser")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUUser(@PathVariable Long id, @RequestBody UUser UUser) {
        UUser u = UUsers.get(id);
        u.setNickname(UUser.getNickname());
        UUsers.put(id, u);
        return "success";
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUUser(@PathVariable Long id) {
        UUsers.remove(id);
        return "success";
    }

}
