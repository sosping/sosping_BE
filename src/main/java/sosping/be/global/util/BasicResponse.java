package sosping.be.global.util;

import org.springframework.http.ResponseEntity;
import sosping.be.global.exception.domain.BusinessException;

public record BasicResponse(
        String response,
        String error,
        String detail
) {
    public static BasicResponse of(BusinessException exception) {
        return new BasicResponse(
                exception.getErrorCode().getMessage(),
                exception.getErrorCode().getCode(),
                exception.getDetail()
        );
    }

    public static BasicResponse of(String response) {
        return new BasicResponse(
                response,
                "",
                ""
        );
    }

    public static ResponseEntity<BasicResponse> to(BusinessException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(BasicResponse.of(exception));
    }
}
