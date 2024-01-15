package sparta.com.sappun.domain.ReportBoard.service;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.ReportBoard.dto.response.ReportBoardGetRes;
import sparta.com.sappun.domain.ReportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.ReportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.board.dto.response.BoardToListGetRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.comment.dto.response.CommentGetRes;
import sparta.com.sappun.domain.comment.entity.Comment;

@Mapper
public interface ReportBoardServiceMapper {
    ReportBoardServiceMapper INSTANCE = Mappers.getMapper(ReportBoardServiceMapper.class);

    @Mapping(source = "board.id", target = "reportedBoardId")
    @Mapping(source = "user.id", target = "reporterUserId")
    ReportBoardRes toReportBoardRes(ReportBoard reportBoard);

    List<ReportBoardGetRes> toReportBoardListGetRes(List<ReportBoard> reportBoards);

    @Mapping(source = "user.nickname", target = "nickname")
    CommentGetRes toCommentGetRes(Comment comment);

    @Mapping(source = "user.nickname", target = "nickname")
    ReportBoardGetRes toBoardGetRes(ReportBoard reportBoard);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardToListGetRes toBoardToListGetRes(Board board);
}
