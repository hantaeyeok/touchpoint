package com.touchpoint.kh.product.resposnse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ResponseData {
	
	private String message;
	private Object responseData;

}
