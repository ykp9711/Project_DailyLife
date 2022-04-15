package com.DailyLife.controller;

import com.DailyLife.dto.Reply;
import com.DailyLife.dto.Board;
import com.DailyLife.dto.User;
import com.DailyLife.mapper.BoardMapper;
import com.DailyLife.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final BoardService boardService;


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
        List<Board> boardList = boardService.findAllBoard();

        model.addAttribute("board" , boardList);

        return "index";
    }

    /**
     * 테스트 컨트롤러
     * @return
     */

    @GetMapping("/test4")
    public String test4(@ModelAttribute Reply reply, Model model) {
        model.addAttribute("replyList", boardService.getAllReply());
        return "coment";
    }

}
