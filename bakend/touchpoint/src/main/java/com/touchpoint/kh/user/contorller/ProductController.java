package com.touchpoint.kh.user.contorller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touchpoint.kh.user.model.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.driver.Message;

@RestController
@Slf4j
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping
	public ResponseEntity<Message> save(@RequestBody String data){
		log.info("앞단에서 받은 데이터:{}" , data);
		log.info("앞단에서 받은 데이터:{}" , data);

		return null;
	}
	
}
