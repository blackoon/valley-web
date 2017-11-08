package com.hylanda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hylanda.model.Operate;
import com.hylanda.model.RedisInfoDetail;
import com.hylanda.service.RedisService;

/** 
 * @author zhangy
 * @E-mail:blackoon88@gmail.com 
 * @qq:846579287
 * @version created at：2017年11月8日 下午3:13:25 
 * note
 */
//@Controller
//@RequestMapping(value="redis")
public class RedisController {
	@Autowired
	RedisService redisService;
	
	//跳转到监控页面
	@RequestMapping(value="redisMonitor")
	public String redisMonitor(Model model) {
		//获取redis的info
		List<RedisInfoDetail> ridList = redisService.getRedisInfo();
		//获取redis的日志记录
		List<Operate> logList = redisService.getLogs(1000);
		//获取日志总数
		long logLen = redisService.getLogLen();
		model.addAttribute("infoList", ridList);
		model.addAttribute("logList", logList);
		model.addAttribute("logLen", logLen);
		return "redisMonitor";
	}
	//清空日志按钮
	@RequestMapping(value="logEmpty")
	@ResponseBody
	public String logEmpty(){
		return redisService.logEmpty();
	}
	
	//获取当前数据库中key的数量
	@RequestMapping(value="getKeysSize")
	@ResponseBody
	public String getKeysSize(){
		return JSON.toJSONString(redisService.getKeysSize());
	}
	
	//获取当前数据库内存使用大小情况
	@RequestMapping(value="getMemeryInfo")
	@ResponseBody
	public String getMemeryInfo(){
		return JSON.toJSONString(redisService.getMemeryInfo());
	}
}
