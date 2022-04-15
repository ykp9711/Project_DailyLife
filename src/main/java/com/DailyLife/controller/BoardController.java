package com.DailyLife.controller;

import com.DailyLife.dto.Board;
import com.DailyLife.dto.Reply;

import com.DailyLife.dto.BoardInfos;
import com.DailyLife.dto.BoardPhoto;
import com.DailyLife.mapper.BoardMapper;
import com.DailyLife.service.BoardService;
import com.DailyLife.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {

//    private final BoardMapper boardMapper;
    private final BoardService boardService;
    private final UserService userService;

    //댓글 작성하기
    @PostMapping("/addReply")
    public String addReply(@ModelAttribute Reply reply, Model model, HttpServletRequest req, HttpServletResponse resp){
        reply.setBno(2L);
        boardService.addReply(reply);
        model.addAttribute("replyList", reply);
        String referer = req.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
        return "redirect:"+ referer; // 이전 페이지로 리다이렉트
    }
    //댓글 삭제하기
    @GetMapping("/deleteReply")
    public String removeReply(int rno, HttpServletRequest req) {
        boardService.removeReplyByUno(rno);
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

    @GetMapping("/addWrite")
    public String getWriteForm() {
        return "board/write";

    }

    @GetMapping("/getwrite")
    public String getWriteForm2() {
        return "board/getwrite";

    }

    @PostMapping("/addWrite")
    @Transactional
    public String addBoard(@ModelAttribute Board board , HttpSession session) throws Exception{

        Long uno = userService.findBySession(session);
        boardService.addBoard(board , uno);

        List<BoardPhoto> boardPhotos = boardService.findAllPhoto();

        return "redirect:/index";
    }

    /**
     * uno 에따른 board , boardPhoto를 조인해서 값을 가져옴  게시물 상세보기
     * @param uno
     * @return List<BoardInfos>
     */

    @GetMapping("/findBoardInfo/{uno}")
    @ResponseBody
    public String getBoardInfoByUno(@PathVariable Long uno) {

        List<BoardInfos> byUno = boardService.findBoardInfoByUno(uno);


        return "getBoardInfo - OK";
    }

    /**
     * bno 에따른 board , boardPhoto를 조인해서 값을 가져옴  게시물 상세보기
     * @param bno
     * @return List<BoardInfos>
     */

    @GetMapping("/findBoardInfo/{bno}")
    @ResponseBody
    public String getBoardInfoByBno(@PathVariable Long bno) {

        List<BoardInfos> byUno = boardService.findBoardInfoByUno(bno);
        return "getBoardInfo - OK";

    }

    /**
     * uno에 따른 boardList를 가져옴.
     * @param uno
     * @return  List<Board>
     */

    @GetMapping("/findBoard/{uno}")
    public String getBoardByUno(@PathVariable Long uno , Model model) {

        List<Board> boards = boardService.findAllBoardByUno(uno);

        model.addAttribute("boards" , boards);

        return "test";

    }

    /**
     * bno 에 따른 boardPhoto를 가져옴
     * @param bno
     * @return List<BoardPhoto>
     */

    @GetMapping("/findBoardPhoto/{bno}")
    @ResponseBody
    public List<BoardPhoto> getBoardPhotoByBno(@PathVariable Long bno , Model model) {

        List<BoardPhoto> boardPhotos = boardService.findAllBoardPhotoByBno(bno);


        return boardPhotos;

    }

    @GetMapping("/updateBoardForm")
    public String updateBoardForm() {
        return "board/updateBoardForm";
    }



    @PostMapping("/updateBoard/{bno}")
    @ResponseBody
    public String updateBoard(@PathVariable Long bno) {

        return "updateBoard - OK";

    }

    /**
     * 이미지 생성.
     */

    @GetMapping("/image/{fileName}")
    @ResponseBody
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        String path = "C:\\Users\\sdm03\\OneDrive\\바탕 화면\\프젝(dl)\\dl-v5\\Project_DailyLife\\src\\main\\resources\\static\\uploadImage/" + fileName;
        return new UrlResource("file:" +path);
    }

}
