package org.example.reactstudy.controller;

import lombok.RequiredArgsConstructor;
import org.example.reactstudy.dto.BoardCreateDto;
import org.example.reactstudy.dto.BoardDetailDto;
import org.example.reactstudy.dto.BoardListDto;
import org.example.reactstudy.dto.BoardUpdateDto;
import org.example.reactstudy.security.JwtUtil;
import org.example.reactstudy.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    // 생성
    @PostMapping("/create")
    public void boardCreate(@RequestHeader("Authorization") String token, @RequestBody BoardCreateDto dto) {
        String realToken = token.substring(7);
        Long userId = Long.valueOf(jwtUtil.getUserId(realToken));
        boardService.BoardCreate(userId, dto);
    }

    // 리스트 반환
    @PostMapping("/")
    public List<BoardListDto> getBoardList(@RequestHeader("Authorization") String token) {
        return boardService.getBoardList();
    }

    // 게시물 반환
    @PostMapping("/{boardId}")
    public BoardDetailDto getBoardDetail(@RequestHeader("Authorization") String token, @PathVariable int boardId) {
        return boardService.getBoardDetail(boardId);
    }

    // 수정
    @PutMapping("/{boardId}")
    public void boardUpdate(@RequestHeader("Authorization") String token, @PathVariable int boardId, @RequestBody BoardUpdateDto dto) {
        String realToken = token.substring(7);
        Long userId = Long.valueOf(jwtUtil.getUserId(realToken));
        boardService.boardUpdate(userId, boardId, dto);
    }

    // 삭제
    @DeleteMapping("/{boardId}")
    public void boardDelete(@RequestHeader("Authorization") String token, @PathVariable int boardId) {
        String realToken = token.substring(7);
        Long userId = Long.valueOf(jwtUtil.getUserId(realToken));
        boardService.boardDelete(userId, boardId);
    }

}
