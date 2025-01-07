package com.touchpoint.kh.user.contorller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.touchpoint.kh.user.model.service.ProductService;
import com.touchpoint.kh.user.model.vo.Product;

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
	public ResponseEntity<Message> save(@RequestBody String data) throws JsonMappingException, JsonProcessingException{
	
		log.info("앞단에서 받은 데이터:{}" , data);
		
		Product product = new ObjectMapper().readValue(data, Product.class);
		product.setCreate_date(LocalDateTime.now());
		
		Product saveObj = productService.save(product);
		
		log.info("반환받은 Produc :{}" , saveObj);

		return null;
	}
	
}
