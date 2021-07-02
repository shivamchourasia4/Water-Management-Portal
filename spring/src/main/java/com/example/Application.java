package com.example;

import com.example.filters.AuthFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		// System.out.println("Working!!!");

	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

		AuthFilter authFilter = new AuthFilter();

		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/admin");
		registrationBean.addUrlPatterns("/admin/*");
		registrationBean.addUrlPatterns("/addInfo");
		registrationBean.addUrlPatterns("/getWaterInfo/*");
		registrationBean.addUrlPatterns("/addFeedback");
		registrationBean.addUrlPatterns("/searchWaterInfo");
		// registrationBean.addUrlPatterns("/admin/feeback");
		// registrationBean.addUrlPatterns("/admin/feeback/*");
		// registrationBean.addUrlPatterns("/admin/sendmail");
		return registrationBean;
	}

}
