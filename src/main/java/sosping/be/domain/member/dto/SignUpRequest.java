package sosping.be.domain.member.dto;

public record SignUpRequest(
        String email,
        String password,
        String name
) {
}
