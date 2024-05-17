package sosping.be.domain.member.dto;

import sosping.be.domain.member.domain.Member;

public record MemberDTO(
        Long memberId,
        String email,
        String name
) {
    public static MemberDTO of(Member member) {
        return new MemberDTO(member.getMemberId(), member.getEmail(), member.getName());
    }
}
