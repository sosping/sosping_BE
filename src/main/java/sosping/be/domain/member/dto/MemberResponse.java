package sosping.be.domain.member.dto;

import sosping.be.domain.member.domain.Member;
import sosping.be.global.jwt.dto.TokenResponse;

public record MemberResponse(
        Member member,
        TokenResponse tokenResponse
) {
    public static MemberResponse of(Member member, String accessToken, String refreshToken) {
        return new MemberResponse(member, new TokenResponse(refreshToken, accessToken));
    }
}
