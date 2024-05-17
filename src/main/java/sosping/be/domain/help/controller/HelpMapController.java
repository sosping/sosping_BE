package sosping.be.domain.help.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sosping.be.domain.help.dto.HelpMessage;

@Controller
@RequiredArgsConstructor
public class HelpMapController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @MessageMapping("/help")
    @SendTo("/topic/locations")
    public HelpMessage sendHelpMessage(HelpMessage message) {
        return message;
    }
}
