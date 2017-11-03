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
import com.hylanda.entity.UPermission;
import com.hylanda.model.UPermissionQo;
import com.hylanda.service.DepartmentService;
import com.hylanda.service.RoleService;
import com.hylanda.service.UPermissionService;

@Controller
@RequestMapping("/uPermission")
public class UPermissionController {
    private static Logger logger = LoggerFactory.getLogger(UPermissionController.class);

    @Autowired
    private UPermissionService uPermissionService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "uPermission/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        UPermission uPermission = uPermissionService.findById(id);
        model.addAttribute("uPermission",uPermission);
        return "uPermission/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<UPermission> getList(UPermissionQo uPermissionQo) {
        try {
            return uPermissionService.findPage(uPermissionQo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,UPermission uPermission){
        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("uPermission", uPermission);
        return "uPermission/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(UPermission uPermission) throws Exception{
        uPermissionService.create(uPermission);
        logger.info("新增->ID="+uPermission.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        UPermission uPermission = uPermissionService.findById(id);

        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();

        List<Long> rids = new ArrayList<Long>();
        for(Role role : uPermission.getRoles()){
            rids.add(role.getId());
        }

        model.addAttribute("uPermission",uPermission);
        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("rids", rids);
        return "uPermission/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(UPermission uPermission) throws Exception{
        uPermissionService.update(uPermission);
        logger.info("修改->ID="+uPermission.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        uPermissionService.delete(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
