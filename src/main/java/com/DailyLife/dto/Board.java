package com.DailyLife.dto;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
@Data

public class Board {
    private long bno;
    private String thumbnail;
    private String content;
    private long uno;
    private java.util.Date Date;
}