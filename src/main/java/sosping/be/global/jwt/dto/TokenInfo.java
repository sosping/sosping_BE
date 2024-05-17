package sosping.be.global.jwt.dto;

import java.util.List;

public record TokenInfo(
        String aud,
        List<String> roles,
        String iat,
        String exp,
        String iss,
        Boolean isValid
) {
}
