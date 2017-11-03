package com.hylanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * @author zhangy
 * @E-mail:blackoon88@gmail.com 
 * @qq:846579287
 * @version created at：2017年10月19日 上午11:24:04 
 * note
 */
@SpringBootApplication
//@EnableJpaRepositories
//@ComponentScan(basePackages = "com.hylanda.*")
@RestController
public class BootStrap {
	@RequestMapping("/")
	String home(){
		return "hello";
	}
	public static void main(String[] args) {
		SpringApplication.run(BootStrap.class, args);
	}
}
