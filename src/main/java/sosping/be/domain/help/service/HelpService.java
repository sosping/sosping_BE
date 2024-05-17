package sosping.be.domain.help.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sosping.be.domain.fcm.service.NotificationService;
import sosping.be.domain.help.dto.HelpMessage;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.repository.MemberRepository;
import sosping.be.global.aspect.LogMonitoring;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HelpService {
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(HelpService.class);

    @LogMonitoring
    public void sendHelp(Member member, Double latitude, Double longitude) {
        member.updateLocation(latitude, longitude);
        member = memberRepository.save(member);

        List<String> nearbyDeviceIds = findNearMembers(latitude, longitude);
        LOGGER.info("[sendHelp] {}", nearbyDeviceIds);

        notificationService.sendHelpCallNotification(
                "구조 요청",
                "주변에 위험에 빠진 사람이 존재해요. 위치를 확인해주세요",
                nearbyDeviceIds,
                latitude,
                longitude
        );

        // Broadcast location to WebSocket clients
        HelpMessage helpMessage = new HelpMessage(latitude, longitude);
        messagingTemplate.convertAndSend("/topic/locations", helpMessage);
        LOGGER.info("[sendHelp] WebSocket message sent: {}", helpMessage);

    }

    @LogMonitoring
    private List<String> findNearMembers(Double latitude, Double longitude) {
        List<Member> nearbyMembers = memberRepository.findMembersWithinDistance(latitude, longitude);
        return nearbyMembers.stream()
                .map(Member::getDeviceId)
                .collect(Collectors.toList());
    }
}
