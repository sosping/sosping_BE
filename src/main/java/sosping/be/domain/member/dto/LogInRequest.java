package sosping.be.domain.member.dto;

public record LogInRequest(
        String email,
        String password
) {
}
