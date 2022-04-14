package com.DailyLife.dto;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Component
@Log4j2
public class Upload {

    public List<String> FileUpload(MultipartFile[] file) throws IOException {
        //String uploadPath = "C:\\Users\\ykp\\git\\Project_Aview\\src\\main\\webapp\\resources\\FileImage"; // 경필 저장 경로

//        String uploadPath = "C:\\Project_DailyLife\\src\\main\\resources\\static\\uploadImage";//정근 저장 주소
        String uploadPath = "C:\\Users\\sdm03\\OneDrive\\바탕 화면\\프젝(dl)\\dl-v5\\Project_DailyLife\\src\\main\\resources\\static\\uploadImage";

        ArrayList<String> fileName = new ArrayList<>(); // 파일 이름들을 저장할 리스트 생성

        File target = new File(uploadPath);
        if(!target.exists()) target.mkdir(); // 파일 경로에 폴더 없으면 새로운 폴더 생성
        for(int i=0; i<file.length; i++) { // 파일 갯수만큼 반복

            String orgFileName = file[i].getOriginalFilename();  //파일 실제이름
            String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf(".")); // 파일 확장자 exe같은거
            String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + orgFileExtension; // 파일 랜덤이름
            Long saveFileSize = file[i].getSize();

            log.info("================== file start ==================");
            log.info("파일 실제 이름: "+orgFileName);
            log.info("파일 저장 이름: "+saveFileName);
            log.info("파일 크기: "+saveFileSize);
            log.info("content type: "+file[i].getContentType());


            target = new File(uploadPath, saveFileName);
            file[i].transferTo(target);
            fileName.add(saveFileName); // 리스트에 파일이름 갯수만큼 저장

        }
        return fileName; // 리스트 리턴
    }
}
