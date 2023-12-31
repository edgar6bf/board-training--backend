package site.board.boardtraining.global.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException
        extends RuntimeException {
    private final ErrorCode errorCode;

    public ResourceNotFoundException(
            final ErrorCode errorCode
    ) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException(
            final ErrorCode errorCode,
            Throwable cause
    ) {
        super(
                errorCode.getMessage(),
                cause
        );
        this.errorCode = errorCode;
    }
}