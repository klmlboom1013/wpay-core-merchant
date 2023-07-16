package com.wpay.core.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@ServletComponentScan
public class WpayCoreMerchantApplication extends SpringBootServletInitializer {

//	public static void main(String[] args) {
//		SpringApplication.run(WpayCoreMerchantApplication.class, args);
//	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(WpayCoreMerchantApplication.class, args);
		DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}
}
