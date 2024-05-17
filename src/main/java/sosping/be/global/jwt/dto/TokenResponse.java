package sosping.be.global.jwt.dto;

public record TokenResponse(
        String refreshToken,
        String accessToken
) {
}
