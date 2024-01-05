package sparta.com.sappun.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    // 글로벌
    SUCCESS(HttpStatus.OK, HttpStatus.OK.value(), "정상 처리 되었습니다"),

    // 유저

    // 보드

    // 댓글

    // 좋아요

    // 신고

    // sample
    NOT_FOUND_SAMPLE(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "존재하지 않는 샘플입니다.");

    private final HttpStatus status;
    private final Integer code;
    private final String message;
}
