package cn.web.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class InitConfig {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct //系统启动初始化该方法
	public void init() {
		System.out.println("jdbcTemplate="+jdbcTemplate);
	}
}
