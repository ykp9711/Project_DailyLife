package com.DailyLife.controller;

import com.DailyLife.dto.Board;
import com.DailyLife.dto.User;
import com.DailyLife.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final BoardMapper boardMapper;

    @GetMapping("/")
    public String main(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "redirect:/index";
        }
        return "main";
    }

    @GetMapping("/index")
    public String testIndex(Model model) {

        List<Board> boardList = boardMapper.findAllBoard();

        model.addAttribute("board" , boardList);

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
