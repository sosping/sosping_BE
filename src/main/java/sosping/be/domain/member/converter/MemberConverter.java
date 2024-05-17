package sosping.be.domain.member.converter;

import org.springframework.stereotype.Component;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.dto.SignUpRequest;

@Component
public class MemberConverter {
    public static Member convert(SignUpRequest request) {
        return Member.builder()
                .email(request.email())
                .name(request.name())
                .build();
    }
}
