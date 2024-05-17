package sosping.be.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.repository.MemberRepository;
import sosping.be.global.exception.ErrorCode;
import sosping.be.global.exception.domain.BusinessException;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(MemberDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long memberId = Long.parseLong(username);

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    LOGGER.info("[loadUserById] {} 없음", memberId);
                    throw new BusinessException(ErrorCode.PLAYER_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
        return member;
    }
}
