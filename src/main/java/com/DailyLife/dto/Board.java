package com.DailyLife.dto;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Board {

    private long bno;
    private String thumbnail;
    private String content;
    private MultipartFile[] photos;
    private long uno;
    private Date date;

}