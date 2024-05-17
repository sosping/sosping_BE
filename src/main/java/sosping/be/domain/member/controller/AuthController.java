package sosping.be.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.dto.LogInRequest;
import sosping.be.domain.member.dto.MemberResponse;
import sosping.be.domain.member.dto.SignUpRequest;
import sosping.be.domain.member.service.AuthService;
import sosping.be.global.aspect.LogMonitoring;
import sosping.be.global.jwt.TokenProvider;

@RestController
@Tag(name = "Auth")
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    private final TokenProvider tokenProvider;

    @LogMonitoring
    @Operation(summary = "회원가입")
    @PostMapping("signup")
    public MemberResponse signUp(SignUpRequest request) {
        Member member = authService.signup(request);

        String accessToken = tokenProvider.generateAccessToken(member);
        String refreshToken = tokenProvider.generateRefreshToken(member);

        return MemberResponse.of(member, accessToken, refreshToken);
    }

    @LogMonitoring
    @Operation(summary = "로그인")
    @PostMapping("login")
    public MemberResponse logIn(LogInRequest request) {
        Member member = authService.login(request);

        String accessToken = tokenProvider.generateAccessToken(member);
        String refreshToken = tokenProvider.generateRefreshToken(member);

        return MemberResponse.of(member, accessToken, refreshToken);
    }




}
