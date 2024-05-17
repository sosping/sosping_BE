package sosping.be.domain.help.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sosping.be.domain.help.service.HelpService;
import sosping.be.domain.member.domain.Member;
import sosping.be.global.aspect.LogMonitoring;

@RestController
@RequiredArgsConstructor
@Tag(name = "Help")
@Table(name = "help_cord")
@RequestMapping("/api/help")
public class HelpController {
    private final HelpService helpService;

    @LogMonitoring
    @PostMapping("location")
    public ResponseEntity<?> sendSOS(@AuthenticationPrincipal Member member,
                                     @RequestParam Double latitude,
                                     @RequestParam Double longitude) {
        helpService.sendHelp(member, latitude, longitude);

        return ResponseEntity.ok(null);
    }
}
