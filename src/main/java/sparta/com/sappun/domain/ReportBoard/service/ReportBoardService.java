package sparta.com.sappun.domain.ReportBoard.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.ReportBoard.dto.request.ReportBoardReq;
import sparta.com.sappun.domain.ReportBoard.dto.response.DeleteReportBoardRes;
import sparta.com.sappun.domain.ReportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.ReportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.ReportBoard.repository.ReportBoardRepository;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.BoardValidator;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportBoardService {
    private final ReportBoardRepository reportBoardRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReportBoardRes reportBoardRes(Long boardId, ReportBoardReq req) {
        Board board = findBoardById(boardId);

        User user = userRepository.findById(req.getUserId());
        UserValidator.validate(user);

        ReportBoard reportBoard =
                ReportBoard.builder().reason(req.getReason()).board(board).user(user).build();

        board.getUser().updateScore(-50); // 신고를 받은 게시글의 작성자 점수 -50

        reportBoardRepository.save(reportBoard);

        return ReportBoardServiceMapper.INSTANCE.toReportBoardRes(reportBoard);
    }

    @Transactional
    public DeleteReportBoardRes deleteReportBoard(Long boardId) {
        Board board = findBoardById(boardId);

        // 허위 신고한 사용자의 점수 -50
        int count = 0; // 신고 횟수
        List<User> reporters = reportBoardRepository.selectUserByBoard(board);
        for(User user : reporters) {
            user.updateScore(-50);
            count++;
        }

        // 신고 취소된 게시글의 작성자의 점수 +50 * 신고 횟수
        board.getUser().updateScore(50 * count);

        // 신고 내역 삭제
        reportBoardRepository.clearReportBoardByBoard(board);

        return new DeleteReportBoardRes();
    }

    private Board findBoardById(Long boardId){
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);
        return board;
    }
}
