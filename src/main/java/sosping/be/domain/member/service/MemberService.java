package sosping.be.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.domain.MemberRoleType;
import sosping.be.domain.member.repository.MemberRepository;
import sosping.be.global.exception.ErrorCode;
import sosping.be.global.exception.domain.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<String> getExperiencesByName(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return member.getExperiences();
    }

    @Transactional
    public void addTutorRole(Long memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    return new BusinessException(ErrorCode.PLAYER_NOT_FOUND, HttpStatus.NOT_FOUND);
                });

        member.updateRole(MemberRoleType.TUTOR);
        memberRepository.save(member);
    }

    @Transactional
    public void addExperience(Member member, String experience) {
        member.getExperiences().add(experience);
        memberRepository.save(member);
    }

    @Transactional
    public String updateDeviceId(Member member, String deviceId) {
        member.updateDeviceId(deviceId);
        return memberRepository.save(member).getDeviceId();
    }

    @Transactional
    public void updateLocation(Member member, Double latitude, Double longitude) {
        member.updateLocation(latitude, longitude);
        memberRepository.save(member);
    }

}
