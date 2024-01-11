package sparta.com.sappun.domain.ReportBoard.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.ReportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.ReportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.board.dto.response.BoardSaveRes;
import sparta.com.sappun.domain.board.entity.Board;

@Mapper
public interface ReportBoardServiceMapper {
    ReportBoardServiceMapper INSTANCE = Mappers.getMapper(ReportBoardServiceMapper.class);

    @Mapping(source = "board.id", target = "reportedBoardId")
    @Mapping(source = "user.id", target = "reporterUserId")
    ReportBoardRes toReportBoardRes(ReportBoard reportBoard);
    }

