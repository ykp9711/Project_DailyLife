package com.DailyLife.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class User {

    private Long uno;
    private String userNickName;
    private String userId;
    private String userPassword;
    private String userPasswordCheck;
    private String userEmail;
    private Integer emailAuthor;

}
