package sosping.be.domain.member.dto;

import sosping.be.domain.member.domain.Member;
import sosping.be.global.jwt.dto.TokenResponse;

public record MemberResponse(
        MemberDTO member,
        TokenResponse tokenResponse
) {
    public static MemberResponse of(MemberDTO member, String accessToken, String refreshToken) {
        return new MemberResponse(member, new TokenResponse(refreshToken, accessToken));
    }
}
