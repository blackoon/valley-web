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
import com.hylanda.entity.URolePermission;
import com.hylanda.model.URolePermissionQo;
import com.hylanda.service.DepartmentService;
import com.hylanda.service.RoleService;
import com.hylanda.service.URolePermissionService;

@Controller
@RequestMapping("/uRolePermission")
public class URolePermissionController {
    private static Logger logger = LoggerFactory.getLogger(URolePermissionController.class);

    @Autowired
    private URolePermissionService uRolePermissionService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "uRolePermission/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        URolePermission uRolePermission = uRolePermissionService.findById(id);
        model.addAttribute("uRolePermission",uRolePermission);
        return "uRolePermission/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<URolePermission> getList(URolePermissionQo uRolePermissionQo) {
        try {
            return uRolePermissionService.findPage(uRolePermissionQo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,URolePermission uRolePermission){
        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("uRolePermission", uRolePermission);
        return "uRolePermission/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(URolePermission uRolePermission) throws Exception{
        uRolePermissionService.create(uRolePermission);
        logger.info("新增->ID="+uRolePermission.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        URolePermission uRolePermission = uRolePermissionService.findById(id);

        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        List<Long> rids = new ArrayList<Long>();
        for(Role role : uRolePermission.getRoles()){
            rids.add(role.getId());
        }

        model.addAttribute("uRolePermission",uRolePermission);
        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("rids", rids);
        return "uRolePermission/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(URolePermission uRolePermission) throws Exception{
        uRolePermissionService.update(uRolePermission);
        logger.info("修改->ID="+uRolePermission.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        uRolePermissionService.delete(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
