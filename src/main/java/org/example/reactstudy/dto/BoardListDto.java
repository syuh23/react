package org.example.reactstudy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BoardListDto {

    private int boardId;

    private String title;

    private String writer;

    private Date createDate;
}
