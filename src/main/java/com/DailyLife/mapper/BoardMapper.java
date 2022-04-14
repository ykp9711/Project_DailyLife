package com.DailyLife.mapper;

import com.DailyLife.dto.Board;
import com.DailyLife.dto.BoardInfos;
import com.DailyLife.dto.BoardPhoto;
import com.DailyLife.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface BoardMapper {

    int addBoard(Board board);
    int addPhoto(BoardPhoto boardPhoto);
    List<Board> findAllBoard();
    List<BoardPhoto> findAllPhoto();
    List<BoardInfos> findByUno(Long uno);
    List<Board> findAllBoardByUno(Long uno);
    List<BoardPhoto> findAllBoardPhotoByBno(Long bno);
}
