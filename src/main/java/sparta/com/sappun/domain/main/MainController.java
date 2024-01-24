package sparta.com.sappun.domain.main;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sparta.com.sappun.domain.board.dto.response.BoardBestListGetRes;
import sparta.com.sappun.domain.board.service.BoardService;
import sparta.com.sappun.global.security.UserDetailsImpl;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class MainController {

    private final BoardService boardService;

    // Best 게시글 조회
    @GetMapping()
    public String getBestBoards(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardBestListGetRes bestBoards = boardService.getBoardBestList();
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        model.addAttribute("bestBoards", bestBoards);
        model.addAttribute("boardList", bestBoards.getBoards());
        return "mainPage";
    }
}
