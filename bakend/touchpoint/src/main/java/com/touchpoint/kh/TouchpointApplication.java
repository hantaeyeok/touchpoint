package com.touchpoint.kh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.touchpoint.kh.history.model.dao")
public class TouchpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(TouchpointApplication.class, args);
	}

}
