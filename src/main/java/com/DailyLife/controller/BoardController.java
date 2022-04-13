package com.DailyLife.controller;

import com.DailyLife.dto.Board;
import com.DailyLife.dto.BoardInfos;
import com.DailyLife.dto.BoardPhoto;
import com.DailyLife.mapper.BoardMapper;
import com.DailyLife.service.BoardService;
import com.DailyLife.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final UserService userService;

    @GetMapping("/write")
    public String getWriteForm() {
        return "board/write";

    }

    @GetMapping("/getwrite")
    public String getWriteForm2() {
        return "board/getwrtie";

    }

    @PostMapping("/write")
    @Transactional
    public String write(@ModelAttribute Board board , HttpSession session) throws Exception{

        Long uno = userService.findBySession(session);
        boardService.addBoard(board , uno);

        List<BoardPhoto> boardPhotos = boardMapper.findAllPhoto();
        System.out.println("boardPhotos = " + boardPhotos);

        return null;
    }

    @GetMapping("/getBoardInfo/{uno}")
    @ResponseBody
    public String getBoardInfo(@PathVariable Long uno) {

        List<BoardInfos> byUno = boardService.findByUno(uno);

        return "getBoardInfo - OK";
    }

    @PostMapping("/updateBoard/{bno}")
    @ResponseBody
    public String updateBoard(@PathVariable Long bno) {

        return "updateBoard - OK";
    }


}
