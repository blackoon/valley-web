package com.hylanda.controller;


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

import com.hylanda.entity.UPermission;
import com.hylanda.model.UPermissionQo;
import com.hylanda.service.UPermissionService;
import com.hylanda.service.URoleService;

@Controller
@RequestMapping("/permission")
public class UPermissionController {
    private static Logger logger = LoggerFactory.getLogger(UPermissionController.class);

    @Autowired
    private UPermissionService uPermissionService;
    @Autowired
    private URoleService uRoleService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "permission/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        UPermission uPermission = uPermissionService.findById(id);
        model.addAttribute("permission",uPermission);
        return "permission/show";
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
//        List<URole> roles = uRoleService.findAll();
//        model.addAttribute("roles",roles);
        model.addAttribute("permission", uPermission);
        return "permission/new";
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
        model.addAttribute("permission",uPermission);
        return "permission/edit";
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
