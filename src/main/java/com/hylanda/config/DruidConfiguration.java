package com.hylanda.config;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/** 
 * @author zhangy
 * @E-mail:blackoon88@gmail.com 
 * @qq:846579287
 * @version created at：2017年10月23日 上午11:06:37 
 * note
 */
@SuppressWarnings("deprecation")
@Configuration
public class DruidConfiguration {
		@Bean
	    public ServletRegistrationBean statViewServle(){
	        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
	        //白名单：
	        servletRegistrationBean.addInitParameter("allow","192.168.1.218,127.0.0.1,192.168.12.160");
	        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的即提示:Sorry, you are not permitted to view this page.
	        servletRegistrationBean.addInitParameter("deny","192.168.1.100");
	        //登录查看信息的账号密码.
	        servletRegistrationBean.addInitParameter("loginUsername","druid");
	        servletRegistrationBean.addInitParameter("loginPassword","12345678");
	        //是否能够重置数据.
	        servletRegistrationBean.addInitParameter("resetEnable","false");
	        return servletRegistrationBean;
	    }

		@Bean
	    public FilterRegistrationBean statFilter(){
	        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
	        //添加过滤规则.
	        filterRegistrationBean.addUrlPatterns("/*");
	        //添加不需要忽略的格式信息.
	        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
	        return filterRegistrationBean;
	    }
}
