package site.board.boardtraining.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
public enum GlobalErrorCode
        implements ErrorCode {

    INVALID_REQUEST_PARAMETER("GB-C-0001", BAD_REQUEST, "유효하지 않은 요청 파라미터입니다."),
    INVALID_REQUEST_METHOD("GB-C-0002", METHOD_NOT_ALLOWED, "유효하지 않은 http 요청 메서드입니다."),
    UNAUTHORIZED_RESOURCE_ACCESS("GB-C-0003", FORBIDDEN, "인가되지 않은 자원 접근입니다."),
    RESOURCE_NOT_FOUND("GB-C-0004", NOT_FOUND, "존재하지 않는 리소스입니다."),

    SERVER_ERROR("GB-S-0001", INTERNAL_SERVER_ERROR, "Internal Server Error");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    GlobalErrorCode(
            String code,
            HttpStatus httpStatus,
            String message
    ) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}