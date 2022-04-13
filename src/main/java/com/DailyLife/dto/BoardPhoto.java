package com.DailyLife.dto;

import lombok.*;

@Getter
@Setter
public class BoardPhoto {

    private Long pno;
    private Long bno;
    private String photoRandomName;

    public BoardPhoto(String photoRandomName) {
        this.photoRandomName = photoRandomName;
    }
}
