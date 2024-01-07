package sparta.com.sappun.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    // 글로벌
    SUCCESS(HttpStatus.OK, "정상 처리 되었습니다"),

    // 유저
    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST, "일치하지 않는 비밀번호 입니다."),

    // 보드

    // 댓글

    // 좋아요

    // 신고

    // sample
    NOT_FOUND_SAMPLE(HttpStatus.NOT_FOUND, "존재하지 않는 샘플입니다.");

    private final HttpStatus status;
    private final String message;
}
