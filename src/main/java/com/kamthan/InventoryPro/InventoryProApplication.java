package com.kamthan.InventoryPro;

import com.kamthan.InventoryPro.config.CompanyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CompanyProperties.class)
public class InventoryProApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryProApplication.class, args);
	}

}
