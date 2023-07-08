package com.wpay.core.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WpayCoreMerchantApplication {

	public static void main(String[] args) {
		SpringApplication.run(WpayCoreMerchantApplication.class, args);
	}

}
