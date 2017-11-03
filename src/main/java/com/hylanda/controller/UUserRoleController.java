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
import com.hylanda.entity.UUserRole;
import com.hylanda.model.UUserRoleQo;
import com.hylanda.service.DepartmentService;
import com.hylanda.service.RoleService;
import com.hylanda.service.UUserRoleService;

@Controller
@RequestMapping("/uUserRole")
public class UUserRoleController {
    private static Logger logger = LoggerFactory.getLogger(UUserRoleController.class);

    @Autowired
    private UUserRoleService uUserRoleService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "uUserRole/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        UUserRole uUserRole = uUserRoleService.findById(id);
        model.addAttribute("uUserRole",uUserRole);
        return "uUserRole/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<UUserRole> getList(UUserRoleQo uUserRoleQo) {
        try {
            return uUserRoleService.findPage(uUserRoleQo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,UUserRole uUserRole){
        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("uUserRole", uUserRole);
        return "uUserRole/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(UUserRole uUserRole) throws Exception{
        uUserRoleService.create(uUserRole);
        logger.info("新增->ID="+uUserRole.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        UUserRole uUserRole = uUserRoleService.findById(id);

        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        List<Long> rids = new ArrayList<Long>();
        for(Role role : uUserRole.getRoles()){
            rids.add(role.getId());
        }

        model.addAttribute("uUserRole",uUserRole);
        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("rids", rids);
        return "uUserRole/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(UUserRole uUserRole) throws Exception{
        uUserRoleService.update(uUserRole);
        logger.info("修改->ID="+uUserRole.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        uUserRoleService.delete(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
