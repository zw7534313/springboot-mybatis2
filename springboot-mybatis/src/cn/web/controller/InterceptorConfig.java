package cn.web.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
	public class InterceptorConfig extends WebMvcConfigurerAdapter {
	    @Bean
	    public AuthorityInterceptorAdapter getValidInterceptor() {
	        return new AuthorityInterceptorAdapter();
	    }
	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        InterceptorRegistration addInterceptor = registry.addInterceptor(getValidInterceptor());
	        // 排除配置
	        addInterceptor.excludePathPatterns("/error");
	        addInterceptor.excludePathPatterns("/login**");
	        // 拦截配置
	        addInterceptor.addPathPatterns("/**");
	    }
	}
