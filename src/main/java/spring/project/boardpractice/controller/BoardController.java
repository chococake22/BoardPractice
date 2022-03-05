package spring.project.boardpractice.controller;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.boardpractice.dto.BoardDto;
import spring.project.boardpractice.service.BoardService;


import java.lang.reflect.Member;
import java.util.List;

@Controller
@AllArgsConstructor // 생성자 주입을 받음
public class BoardController {

    private BoardService boardService;

    // 게시물 목록 보기
    @GetMapping("/")
    public String list(Pageable pageable, Model model, @RequestParam(required = false, defaultValue = "") String search) {
        Page<BoardDto> boards = boardService.getBoards(pageable, search);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    // 게시물 작성
    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    // 게시물 작성 확인
    @PostMapping("/write")
    public String write(BoardDto boardDto) {
        boardService.save(boardDto);
        return "redirect:/";
    }

    // 상세 정보
    @GetMapping("/write/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.getBoard(id);

        model.addAttribute("boardDto", boardDto);
        return "board/detail";
    }

    // 게시물 수정
    @GetMapping("/write/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.getBoard(id);
        model.addAttribute("boardDto", boardDto);
        return "board/update";
    }

    // 게시물 수정 확인
    @PutMapping("/write/edit/{id}")
    public String update(BoardDto boardDto) {
        boardService.save(boardDto);
        return "redirect:/";
    }

    @DeleteMapping("/write/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return "redirect:/";
    }

    // 게시물 검색
    @GetMapping("/board/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
        model.addAttribute("boards", boardDtoList);
        return "board/list";
    }


}
