package com.DailyLife.controller;

import com.DailyLife.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String main(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "redirect:/index";
        }
        return "main";
    }

    @GetMapping("/index")
    public String testIndex() {
        return "index";
    }

    @GetMapping("/test")
    public String test() {
        return "Write";
    }

    @GetMapping("/test2")
    public String test2() {
        return "indexsub";
    }

    @GetMapping("/test3")
    public String test3() {
        return "directmessage";
    }

}
