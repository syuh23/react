package org.example.reactstudy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BoardDetailDto {

    private String title;

    private String content;

    private String writer;

    private Date createDate;
}
