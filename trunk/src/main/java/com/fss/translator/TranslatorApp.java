package com.fss.translator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


/**
 * Main class of Translator Project
 * @author ravinaganaboyina
 *
 */
@EnableCaching
@SpringBootApplication(exclude={SecurityAutoConfiguration.class,ManagementWebSecurityAutoConfiguration.class})
public class TranslatorApp extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(TranslatorApp.class, args);
		
	}
}
