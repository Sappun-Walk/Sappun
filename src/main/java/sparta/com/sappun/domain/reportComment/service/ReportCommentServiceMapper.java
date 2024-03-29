package sparta.com.sappun.domain.reportComment.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.reportComment.dto.response.CommentToReportGetRes;
import sparta.com.sappun.domain.reportComment.dto.response.ReportCommentGetRes;
import sparta.com.sappun.domain.reportComment.dto.response.ReportCommentRes;
import sparta.com.sappun.domain.reportComment.entity.ReportComment;

@Mapper
public interface ReportCommentServiceMapper {
    ReportCommentServiceMapper INSTANCE = Mappers.getMapper(ReportCommentServiceMapper.class);

    @Mapping(source = "comment.id", target = "reportCommentId")
    @Mapping(source = "user.id", target = "reporterUserId")
    ReportCommentRes toReportCommentRes(ReportComment reportComment);

    @Mapping(source = "user.nickname", target = "nickname")
    @Mapping(source = "comment.board.id", target = "boardId")
    ReportCommentGetRes toReportCommentGetRes(ReportComment reportComment);

    @Mapping(source = "user.nickname", target = "nickname")
    CommentToReportGetRes toCommentToReportGetRes(Comment comment);
}
