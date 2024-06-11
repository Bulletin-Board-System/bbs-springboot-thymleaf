package org.bbsgroup.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        // 首页从 "/" 重定向到 "/app/index"
        return "redirect:/app/index";
    }
}
