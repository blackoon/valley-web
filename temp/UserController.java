package com.hylanda.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hylanda.entity.Department;
import com.hylanda.entity.Role;
import com.hylanda.entity.User;
import com.hylanda.model.UserQo;
import com.hylanda.service.DepartmentService;
import com.hylanda.service.RoleService;
import com.hylanda.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "user/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "user/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<User> getList(UserQo userQo) {
        try {
            return userService.findPage(userQo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,User user){
        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "user/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(User user) throws Exception{
        userService.create(user);
        logger.info("新增->ID="+user.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "user/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(User user) throws Exception{
        userService.update(user);
        logger.info("修改->ID="+user.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        userService.delete(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
