package com.touchpoint.kh.user.contorller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class UserTestController {

	@GetMapping("/aa")
	public String aa() {
		return "aa";
	}
}
