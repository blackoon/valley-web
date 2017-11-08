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
import com.hylanda.entity.URole;
import com.hylanda.entity.UUser;
import com.hylanda.model.UUserQo;
import com.hylanda.service.DepartmentService;
import com.hylanda.service.URoleService;
import com.hylanda.service.UUserService;

@Controller
@RequestMapping("/user")
public class UUserController {
    private static Logger logger = LoggerFactory.getLogger(UUserController.class);

    @Autowired
    private UUserService uUserService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private URoleService uRoleService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "user/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        UUser uUser = uUserService.findById(id);
        model.addAttribute("user",uUser);
        return "user/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<UUser> getList(UUserQo uUserQo) {
        try {
            return uUserService.findPage(uUserQo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,UUser uUser){
        List<Department> departments = departmentService.findAll();
        List<URole> roles=uRoleService.findAll();
        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("user", uUser);
        return "user/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(UUser uUser) throws Exception{
        uUserService.create(uUser);
        logger.info("新增->ID="+uUser.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        UUser uUser = uUserService.findById(id);
        List<URole> roles=uRoleService.findAll();
        List<Department> departments = departmentService.findAll();
        
        List<Long> rids = new ArrayList<Long>();
        for(URole role : uUser.getRoles()){
            rids.add(role.getId());
        }
        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("user",uUser);
        model.addAttribute("rids", rids);
        return "user/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(UUser uUser) throws Exception{
        uUserService.update(uUser);
        logger.info("修改->ID="+uUser.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        uUserService.delete(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
