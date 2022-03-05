package spring.project.boardpractice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.boardpractice.domain.Board;
import spring.project.boardpractice.dto.BoardDto;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<BoardDto> findAllByContent(String str, Pageable pageable);

    List<Board> findByTitleContaining(String keyword);

    Page<BoardDto> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
