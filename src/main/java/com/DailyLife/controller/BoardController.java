package com.DailyLife.controller;

import com.DailyLife.dto.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    /**
     *  /board/{userNum}/{userName}/write -> 글쓰기
     *  /board/{boardNum}/update -> 글업데이트
     *  /board/{boardNum}/delete -> 글삭제
     *  /board/list -> 리스트
     */

    @GetMapping("/{userNum}/{userNickName}/write")
    public String writeForm(Model model , @PathVariable Long userNum ,  @PathVariable String userNickName) {
        model.addAttribute("board" , Board.builder().userNum(userNum).userNickName(userNickName).build());
        return "board/writeForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board) {
//      boardService.save(board);

        return "redirect:/index";
    }


}
