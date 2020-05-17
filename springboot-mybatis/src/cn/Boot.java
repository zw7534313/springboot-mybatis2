package cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@MyBadisLoader({"saas = com.llc.admin.web.dao.saas = classpath:mybatis/*.xml"}) 
@SpringBootApplication
@EnableTransactionManagement
//@EnableScheduling
public class Boot {

	public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }
	
	// 通过Java配置视图解析器
    /*@Bean
    @ConditionalOnMissingBean(InternalResourceViewResolver.class)
    public InternalResourceViewResolver defaultViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        //resolver.setPrefix("/");
        resolver.setSuffix(".html");
        return resolver;
    }*/
}

