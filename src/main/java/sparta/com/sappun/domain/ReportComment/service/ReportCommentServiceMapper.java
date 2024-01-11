package sparta.com.sappun.domain.ReportComment.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sparta.com.sappun.domain.ReportComment.dto.response.ReportCommentRes;
import sparta.com.sappun.domain.ReportComment.entity.ReportComment;

@Mapper
public interface ReportCommentServiceMapper {
    ReportCommentServiceMapper INSTANCE = Mappers.getMapper(ReportCommentServiceMapper.class);


    @Mapping(source = "comment.id", target = "reportCommentId")
    @Mapping(source = "user.id", target = "reporterUserId")
    ReportCommentRes toReportCommentRes(ReportComment reportComment);
}
