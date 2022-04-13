package com.DailyLife.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class User {

    private Long uno;
//    @Pattern(regexp = "^{5,15}(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=_]).*$" )
    private String userNickName;
//    @Pattern(regexp = "^[a-zA-Z0-9]{5,15}$")
    private String userId;
//    @Pattern(regexp = "^{8,20}(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=_]).*$" )
    private String userPassword;
    private String userEmail;

    private Integer emailAuthor;

}
