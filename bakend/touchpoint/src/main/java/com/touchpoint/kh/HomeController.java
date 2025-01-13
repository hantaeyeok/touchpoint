package com.touchpoint.kh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/**")
    public String redirectReact() {
        // 외부 URL로 리다이렉트
        return "redirect:http://localhost:3000/";
    }
}