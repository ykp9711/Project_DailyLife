package com.DailyLife.dto;

import lombok.Builder;
import lombok.Data;

/**
 * create table board (
 *               boardNum bigint primary key ,
 *               userNum bigint,
 *               userNickName varchar(50),
 *               writeUser varchar(50),
 *                    foreign key (userNum)
 *                    references user(userNum) ,
 *                    foreign key (userNickName)
 *                    references user(userNickName)
 * );
 */

@Data
public class Board {

    private Long boardNum;
    private Long userNum;
    private String userNickName;
    private String writeUser;

    @Builder
    public Board(Long userNum, String userNickName) {
        this.userNum = userNum;
        this.userNickName = userNickName;
    }
}
