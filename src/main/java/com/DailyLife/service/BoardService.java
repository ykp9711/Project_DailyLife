package com.DailyLife.service;

import com.DailyLife.dto.*;
import com.DailyLife.infra.file.Upload;
import com.DailyLife.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final Upload upload;

    public void addBoard(Board board , Long uno) {
        if(board.getPhotos()!= null) {
            try {
                ArrayList<String> fileNameList = (ArrayList<String>) upload.FileUpload(board.getPhotos());
                board.setUno(uno);
                board.setThumbnail(fileNameList.get(0));
                boardMapper.addBoard(board);

                for (String saveFileName: fileNameList) {
                    boardMapper.addPhoto(new BoardPhoto(saveFileName));
                }
            } catch (IOException e) {
                throw new RuntimeException("파일 오류입니다.");
            }
            
        }

    }


    public List<BoardInfos> findBoardInfoByUno(Long uno) {

        List<BoardInfos> boardInfos = boardMapper.findBoardInfoByUno(uno);

        return boardInfos;
    }

    public void addReply(Reply reply) {
        boardMapper.addReply(reply);

    }

    public void removeReplyByUno(int rno) {
        boardMapper.removeReplyByUno(rno);

    }

    public List<BoardPhoto> findAllPhoto() {
        return boardMapper.findAllPhoto();
    }

    public List<Board> findAllBoardByUno(Long uno) {
        return boardMapper.findAllBoardByUno(uno);

    }

    public List<BoardPhoto> findAllBoardPhotoByBno(Long bno) {

        return boardMapper.findAllBoardPhotoByBno(bno);
    }

    public List<Board> findAllBoard() {

        return boardMapper.findAllBoard();

    }

    public List<Reply> getAllReply() {

        return boardMapper.getAllReply();

    }
}
