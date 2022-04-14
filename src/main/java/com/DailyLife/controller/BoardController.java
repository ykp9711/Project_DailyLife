package com.DailyLife.controller;

import com.DailyLife.dto.Board;
import com.DailyLife.dto.Reply;
import com.DailyLife.dto.Upload;

import com.DailyLife.dto.BoardInfos;
import com.DailyLife.dto.BoardPhoto;
import com.DailyLife.mapper.BoardMapper;
import com.DailyLife.service.BoardService;
import com.DailyLife.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardMapper boardMapper;
    @Autowired
    Upload up;


    //댓글 작성하기
    @PostMapping("addReply")
    public String addReply(@ModelAttribute Reply reply, Model model, HttpServletRequest req, HttpServletResponse resp){
        reply.setBno(2L);
        boardMapper.addReply(reply);
        model.addAttribute("replyList", reply);
        String referer = req.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
        return "redirect:"+ referer; // 이전 페이지로 리다이렉트
    }
    //댓글 삭제하기
    @GetMapping("/removeReply")
    public String removeReply(int rno, HttpServletRequest req) {
        boardMapper.removeReply(rno);
        String referer = req.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
        return "redirect:"+ referer; // 이전 페이지로 리다이렉트
    }

    /*//댓글 수정 ajax
    @PostMapping(value ="/modifyReply", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public ResponseEntity<AcademyBoardReviewVO> reviewModify(int rno) {
        AcademyBoardReviewVO vo = mapper.getOneReview(rno);

        return new ResponseEntity<AcademyBoardReviewVO>(vo,org.springframework.http.HttpStatus.OK);
    }
    //댓글 수정
    @PostMapping("/updateReview")
    public String updateReivew(@RequestParam int rno,@RequestParam int ano, @RequestParam String content2) {
        AcademyBoardReviewVO vo = new AcademyBoardReviewVO();
        vo.setRno(rno);
        vo.setContent(content2);
        mapper.updateReview(vo);
        return "redirect:/board/getBoard?ano="+ano;
    }*/

    private final BoardService boardService;
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
    public String addBoard(@ModelAttribute Board board , HttpSession session) throws Exception{

        Long uno = userService.findBySession(session);
        boardService.addBoard(board , uno);

        List<BoardPhoto> boardPhotos = boardMapper.findAllPhoto();
        System.out.println("boardPhotos = " + boardPhotos);

        return "redirect:/index";
    }

    /**
     * uno 에따른 board , boardPhoto를 조인해서 값을 가져옴  게시물 상세보기
     * @param uno
     * @return List<BoardInfos>
     */

    @GetMapping("/getBoardInfo/{uno}")
    @ResponseBody
    public String getBoardInfoByUno(@PathVariable Long uno) {

        List<BoardInfos> byUno = boardService.findByUno(uno);
        return "getBoardInfo - OK";
    }

    /**
     * bno 에따른 board , boardPhoto를 조인해서 값을 가져옴  게시물 상세보기
     * @param bno
     * @return List<BoardInfos>
     */

    @GetMapping("/getBoardInfo/{bno}")
    @ResponseBody
    public String getBoardInfoByBno(@PathVariable Long bno) {

        List<BoardInfos> byUno = boardService.findByUno(bno);
        return "getBoardInfo - OK";

    }



    /**
     * uno에 따른 boardList를 가져옴.
     * @param uno
     * @return  List<Board>
     */

    @GetMapping("/getBoard/{uno}")
    public String getBoardByUno(@PathVariable Long uno , Model model) {

        List<Board> boards = boardMapper.findAllBoardByUno(uno);

        model.addAttribute("boards" , boards);

        return "test";

    }

    /**
     * bno 에 따른 boardPhoto를 가져옴
     * @param bno
     * @return List<BoardPhoto>
     */

    @GetMapping("/getBoardPhoto/{bno}")
    @ResponseBody
    public List<BoardPhoto> getBoardPhotoByBno(@PathVariable Long bno , Model model) {

        List<BoardPhoto> boardPhotos = boardMapper.findAllBoardPhotoByBno(bno);

        System.out.println("boardPhotos = " + boardPhotos);

        return boardPhotos;

    }

    @GetMapping("/updateBoardForm")
    public String updateBoardForm() {
        return "board/updateboardForm";
    }



    @PostMapping("/updateBoard/{bno}")
    @ResponseBody
    public String updateBoard(@PathVariable Long bno) {

        //bno -> 값을 넣고 ,


        return "updateBoard - OK";

    }

    /**
     * 이미지 생성.
     */

    @GetMapping("/image/{fileName}")
    @ResponseBody
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        System.out.println("fileName = " + fileName);
        String path = "C:\\Users\\sdm03\\OneDrive\\바탕 화면\\프젝(dl)\\dl-v5\\Project_DailyLife\\src\\main\\resources\\static\\uploadImage/" + fileName;
        return new UrlResource("file:" +path);
    }

}
