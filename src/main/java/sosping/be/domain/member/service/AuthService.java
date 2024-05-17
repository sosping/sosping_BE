package sosping.be.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sosping.be.domain.member.converter.MemberConverter;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.dto.LogInRequest;
import sosping.be.domain.member.dto.SignUpRequest;
import sosping.be.domain.member.repository.MemberRepository;
import sosping.be.global.exception.ErrorCode;
import sosping.be.global.exception.domain.BusinessException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Member signup(SignUpRequest request) {
        Member member = MemberConverter.convert(request);
        member.updatePassword(passwordEncoder.encode(request.password()));

        return memberRepository.save(member);
    }

    public Member login(LogInRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_EMAIL_OR_PASSWORD, HttpStatus.BAD_REQUEST);
        }
        return member;
    }

}
