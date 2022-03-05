package spring.project.boardpractice.service;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.boardpractice.domain.Board;
import spring.project.boardpractice.dto.BoardDto;
import spring.project.boardpractice.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;

    // 전체 게시물 조회
    @Transactional
    public Page<BoardDto> getBoards(Pageable pageable, @RequestParam(required = false, defaultValue = "") String search) {

        int page;

        if(pageable.getPageNumber() == 0) {
            page = 0;
        } else {
            page = pageable.getPageNumber() - 1;
        }

        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Board> p = boardRepository.findAll(pageable);

        // 검색어가 존재하면
        if(search != "") {
            return boardRepository.findByTitleContainingOrContentContaining(search, search, pageable);
        }

        // Board -> Dto로 변환
//        Page<BoardDto> dtoPage = p.map(new Function<Board, BoardDto>() {
//            @Override
//            public BoardDto apply(Board board) {
//                BoardDto dto = new BoardDto();
//                return dto;
//            }
//        });

        return boardRepository.;
    }

    // 게시물 가져오기
    @Transactional
    public BoardDto getBoard(Long id) {

        // boardWrapper가 null값을 가질 수도 있기 때문에 NPE를 예뱡하기 위해서 Wrapper 클래스를 사용한다.
        Optional<Board> boardWrapper = boardRepository.findById(id);

        // 여기서 엔티티만 가져오기
        Board board = boardWrapper.get();

        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdDate(board.getCreatedDate())
                .build();

        return boardDto;
    }


    // 게시물 작성
    @Transactional
    public Long save(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }


    // 검색하기
    @Transactional
    public List<BoardDto> searchPosts(String keyword) {

        // 제목에 해당 검색어가 포함된 게시물은 모두 가져온다.
        List<Board> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        // 아무것도 없으면 그냥 아무것도 없는 거 리턴
        if(boardEntities.isEmpty()) {
            return boardDtoList;
        }

        // 모든 게시물을 Dto 객체로 변환한다.
        for(Board board : boardEntities) {
            boardDtoList.add(this.toDto(board));
        }

        return boardDtoList;
    }

    private BoardDto toDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdDate(board.getCreatedDate())
                .build();
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }


}
