<<<<<<<< HEAD:bakend/touchpoint/src/main/java/com/touchpoint/kh/user/model/vo/LoginRequest.java
package com.touchpoint.kh.user.model.vo;
========
package com.touchpoint.kh.product.resposnse;
>>>>>>>> origin/main-subb-0113:bakend/touchpoint/src/main/java/com/touchpoint/kh/product/resposnse/ResponseData.java

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
<<<<<<<< HEAD:bakend/touchpoint/src/main/java/com/touchpoint/kh/user/model/vo/LoginRequest.java
public class LoginRequest {

	private String userId;
	private String password;
	private String captchaToken;
	
========
public class ResponseData {
	
	private String message;
	private Object responseData;

>>>>>>>> origin/main-subb-0113:bakend/touchpoint/src/main/java/com/touchpoint/kh/product/resposnse/ResponseData.java
}
