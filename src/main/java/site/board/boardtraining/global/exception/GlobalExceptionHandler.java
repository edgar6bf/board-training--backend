package site.board.boardtraining.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.board.boardtraining.global.response.error.ErrorApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorApiResponse> handleBusinessException(
            BusinessException e
    ) {
        log.error("[handle BusinessException] - {}", e.getMessage());
        return new ResponseEntity<>(
                ErrorApiResponse.of(
                        e.getErrorCode()
                ),
                e.getErrorCode().getHttpStatus()
        );
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorApiResponse> handleResourceNotFoundException(
            ResourceNotFoundException e
    ) {
        log.error("[handle ResourceNotFoundException] - {}", e.getMessage());

        return new ResponseEntity<>(
                ErrorApiResponse.of(
                        e.getErrorCode()
                ),
                e.getErrorCode().getHttpStatus()
        );
    }
}