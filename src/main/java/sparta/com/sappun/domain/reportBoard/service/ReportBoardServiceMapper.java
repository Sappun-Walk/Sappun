package sparta.com.sappun.domain.reportBoard.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.board.dto.response.BoardToReportGetRes;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.reportBoard.dto.response.ReportBoardGetRes;
import sparta.com.sappun.domain.reportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.reportBoard.entity.ReportBoard;

@Mapper
public interface ReportBoardServiceMapper {
    ReportBoardServiceMapper INSTANCE = Mappers.getMapper(ReportBoardServiceMapper.class);

    @Mapping(source = "board.id", target = "reportedBoardId")
    @Mapping(source = "user.id", target = "reporterUserId")
    ReportBoardRes toReportBoardRes(ReportBoard reportBoard);

    @Mapping(source = "user.nickname", target = "nickname")
    ReportBoardGetRes toReportBoardGetRes(ReportBoard reportBoard);

    @Mapping(source = "user.nickname", target = "nickname")
    BoardToReportGetRes toBoardToReportGetRes(Board board);
}
