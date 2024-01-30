package sparta.com.sappun.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    // 글로벌
    SUCCESS(HttpStatus.OK, "정상 처리 되었습니다"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "재로그인 해주세요."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다."),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다."),
    NULL_FILE_TYPE(HttpStatus.BAD_REQUEST, "해당 파일의 확장자를 찾을 수 없습니다."),
    INVALID_PROFILE_IMAGE_FILE(HttpStatus.BAD_REQUEST, "이미지 파일만 업로드 가능합니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "입력값 형식에 맞지 않습니다."),
    MAXIMUM_UPLOAD_FILE_SIZE(HttpStatus.BAD_REQUEST, "파일 용량을 초과하였습니다."),

    // 유저
    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "중복된 아이디 입니다."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임 입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    DIFFERENT_PASSWORD(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다."),

    // 보드
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),

    // 댓글
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    // 좋아요

    // 신고
    DUPLICATED_REPORT_BOARD(HttpStatus.CONFLICT, "이미 신고한 게시글입니다."),
    DUPLICATED_REPORT_COMMENT(HttpStatus.CONFLICT, "이미 신고한 댓글입니다."),

    // sample
    NOT_FOUND_SAMPLE(HttpStatus.NOT_FOUND, "존재하지 않는 샘플입니다.");

    private final HttpStatus status;
    private final String message;
}
