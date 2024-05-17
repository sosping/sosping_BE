package sosping.be.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.domain.MemberRoleType;
import sosping.be.domain.member.repository.MemberRepository;
import sosping.be.global.exception.ErrorCode;
import sosping.be.global.exception.domain.BusinessException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void addTutorRole(Long memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    return new BusinessException(ErrorCode.PLAYER_NOT_FOUND, HttpStatus.NOT_FOUND);
                });

        member.updateRole(MemberRoleType.TUTOR);
        memberRepository.save(member);
    }

}
