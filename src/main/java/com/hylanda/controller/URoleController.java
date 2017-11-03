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
import com.hylanda.entity.URole;
import com.hylanda.model.URoleQo;
import com.hylanda.service.DepartmentService;
import com.hylanda.service.RoleService;
import com.hylanda.service.URoleService;

@Controller
@RequestMapping("/uRole")
public class URoleController {
    private static Logger logger = LoggerFactory.getLogger(URoleController.class);

    @Autowired
    private URoleService uRoleService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "uRole/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        URole uRole = uRoleService.findById(id);
        model.addAttribute("uRole",uRole);
        return "uRole/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<URole> getList(URoleQo uRoleQo) {
        try {
            return uRoleService.findPage(uRoleQo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,URole uRole){
        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("uRole", uRole);
        return "uRole/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(URole uRole) throws Exception{
        uRoleService.create(uRole);
        logger.info("新增->ID="+uRole.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        URole uRole = uRoleService.findById(id);

        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        List<Long> rids = new ArrayList<Long>();
        for(Role role : uRole.getRoles()){
            rids.add(role.getId());
        }

        model.addAttribute("uRole",uRole);
        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("rids", rids);
        return "uRole/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(URole uRole) throws Exception{
        uRoleService.update(uRole);
        logger.info("修改->ID="+uRole.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        uRoleService.delete(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
