package org.example.reactstudy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.reactstudy.dto.BoardUpdateDto;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false, updatable = false)
    private int id;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    @Builder.Default
    private Date created = new Date();

    public void boardUpdate(BoardUpdateDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}
