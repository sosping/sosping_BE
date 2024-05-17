package sosping.be.global.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;

@Component
public class StompSessionHandler extends StompSessionHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        LOGGER.info("Client connected: {}", session.getSessionId());
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        // 메시지 처리 로직을 여기에 작성할 수 있습니다.
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        LOGGER.error("Transport error: {}", exception.getMessage());
    }

}
