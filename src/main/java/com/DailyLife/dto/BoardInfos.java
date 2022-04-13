package com.DailyLife.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BoardInfos {

    private Long bno;
    private Long uno;
    private String content;
    private Date date;
    private String thumbnail;
    private String pno;
    private String photoRandomName;

}
