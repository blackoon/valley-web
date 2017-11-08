package com.hylanda.controller;

import java.util.List;
import java.util.UUID;

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

import com.hylanda.entity.Account;
import com.hylanda.entity.URole;
import com.hylanda.entity.dict.EncodeType;
import com.hylanda.entity.dict.StatusType;
import com.hylanda.model.AccountQo;
import com.hylanda.service.AccountService;
import com.hylanda.service.URoleService;

@Controller
@RequestMapping("/account")
public class AccountController {
    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;
//    @Autowired
//    private DepartmentService departmentService;
    @Autowired
    private URoleService roleService;

    @RequestMapping("/index")
    public String index() throws Exception{
        return "account/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        Account account = accountService.findById(id);
        List<URole> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("account",account);
        model.addAttribute("encodeTypes", EncodeType.getAllEncodeType());
        model.addAttribute("StatusTypes", StatusType.getAllEncodeType());
        return "account/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<Account> getList(AccountQo accountQo) {
        try {
            return accountService.findPage(accountQo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,Account account){
//        List<Department> departments = departmentService.findAll();
        List<URole> roles = roleService.findAll();
//        model.addAttribute("departments",departments);
        model.addAttribute("roles", roles);
        model.addAttribute("account", account);
        model.addAttribute("encodeTypes", EncodeType.getAllEncodeType());
        model.addAttribute("StatusTypes", StatusType.getAllEncodeType());
        return "account/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(Account account) throws Exception{
    	account.setAccessKey(UUID.randomUUID().toString());
        accountService.create(account);
        logger.info("新增->ID="+account.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        Account account = accountService.findById(id);
        List<URole> roles = roleService.findAll();
        model.addAttribute("account",account);
        model.addAttribute("roles", roles);
        model.addAttribute("encodeTypes", EncodeType.getAllEncodeType());
        model.addAttribute("StatusTypes", StatusType.getAllEncodeType());
        return "account/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(Account account) throws Exception{
        accountService.update(account);
        logger.info("修改->ID="+account.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        accountService.delete(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
