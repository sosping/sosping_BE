package sosping.be.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.service.MemberService;
import sosping.be.global.aspect.LogMonitoring;
import sosping.be.global.util.BasicResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member")
@Table(name = "member")
@RequestMapping("api/member")
public class MemberController {
    private final MemberService memberService;

    @LogMonitoring
    @PostMapping("{deviceId}")
    public ResponseEntity<String> updateDeviceId(@AuthenticationPrincipal Member member, @PathVariable String deviceId) {
        String response = memberService.updateDeviceId(member, deviceId);

        return ResponseEntity.ok(response);
    }

    @LogMonitoring
    @Operation(summary = "좌표 갱신", description = "좌표를 갱신합니다. 쿼리 파라미터임 `위도` : `latitude`, `경도`: `longitude` ")
    @PostMapping("location")
    public ResponseEntity<BasicResponse> updateLocation(@AuthenticationPrincipal Member member,
                                                 @RequestParam Double latitude,
                                                 @RequestParam Double longitude) {
        memberService.updateLocation(member, latitude, longitude);
        return ResponseEntity.ok(BasicResponse.of("SUCCESS"));
    }
}