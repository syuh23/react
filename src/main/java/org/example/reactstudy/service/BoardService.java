package org.example.reactstudy.service;

import lombok.RequiredArgsConstructor;
import org.example.reactstudy.dto.BoardCreateDto;
import org.example.reactstudy.dto.BoardDetailDto;
import org.example.reactstudy.dto.BoardListDto;
import org.example.reactstudy.dto.BoardUpdateDto;
import org.example.reactstudy.entity.Board;
import org.example.reactstudy.entity.User;
import org.example.reactstudy.repository.BoardRepository;
import org.example.reactstudy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void BoardCreate(Long userId, BoardCreateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("일치하는 회원이 없습니다."));

        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();

        boardRepository.save(board);
    }

    public List<BoardListDto> getBoardList() {
        List<Board> getBoards = boardRepository.findAll();

        return getBoards.stream()
                .map(board -> BoardListDto.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .writer(board.getUser().getName())
                        .createDate(board.getCreated())
                        .build())
                .toList();
    }

    public BoardDetailDto getBoardDetail(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("없는 게시물입니다."));

        return BoardDetailDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getUser().getName())
                .createDate(board.getCreated())
                .build();
    }

    public void boardUpdate(Long userId, int boardId, BoardUpdateDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("없는 게시물입니다."));

        if (!Objects.equals(userId, board.getUser().getId())) {
            throw new RuntimeException();
        }

        board.boardUpdate(dto);
        boardRepository.save(board);
    }

    public void boardDelete(Long userId, int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("없는 게시물입니다."));

        if (!Objects.equals(board.getUser().getId(), userId)) {
            throw new RuntimeException();
        }

        boardRepository.deleteById(boardId);
    }
}
