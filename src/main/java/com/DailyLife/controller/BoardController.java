package com.DailyLife.controller;

import com.DailyLife.dto.Board;
import com.DailyLife.dto.Upload;
import com.DailyLife.dto.UserPhoto;
import com.DailyLife.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardMapper boardMapper;
    @Autowired
    Upload up;
    @GetMapping("/write")
    public String getWriteForm() {

        return "board/write";

    }

    @GetMapping("/getwrite")
    public String getWriteForm2() {

        return "board/getwrite";

    }

    @PostMapping("/write")
    @Transactional
    public String write(Board bo, MultipartFile[] file, MultipartFile[] UserPhoto, MultipartFile[] photos, UserPhoto userPhoto)throws Exception{
        if(photos[0].getSize()!= 0) {
            ArrayList<String> fileName = new ArrayList<>(); // 파일 이름들을 받을 리스트 생성
            fileName=(ArrayList<String>) up.FileUpload(photos); // 받아온 파일 이름들을 리스트에 저장
            for(int i=0; i<fileName.size(); i++) { // 리스트 크기만큼 반복
                userPhoto.setPhotoRandomName(fileName.get(i)); // 파일들을 DB에 저장
                userPhoto.setBno(1L); // bno값 임시로 1로 설정함 ----------차후에 수정
                boardMapper.addPhoto(userPhoto);
            }
        }
        bo.setUno(1); // 유저 uno 임시로 1로 넣어줌 ------ 차후에 수정
        log.info(bo);
        boardMapper.addBoard(bo);

        return null;
    }


}
