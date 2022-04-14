package com.DailyLife.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Reply {
    private Long rno;
    private Long bno;
    private String reply;
    private String userId;
    Date date;
}
