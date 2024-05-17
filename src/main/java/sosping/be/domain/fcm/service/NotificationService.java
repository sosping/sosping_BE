package sosping.be.domain.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

    private final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private final ApnsConfig apnsConfig = ApnsConfig.builder()
            .putHeader("apns-priority", "10")
            .setAps(Aps.builder()
                    .setBadge(1)
                    .setSound("default")
                    .build())
            .build();
    private final AndroidConfig androidConfig = AndroidConfig.builder()
            .setPriority(AndroidConfig.Priority.HIGH)
            .setNotification(AndroidNotification.builder()
                    .setSound("default")
                    .build())
            .build();

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                    ).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(firebaseOptions);
                LOGGER.info("[Firebase] application initialized");
            }
        } catch (IOException exception) {
            LOGGER.info("init {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Async
    public void sendHelpCallNotification(String title, String body, List<String> deviceIdList, Double latitude, Double longitude) {
        Map<String, String> data = new HashMap<>();
        data.put("contentType", "help");
        data.put("latitude", latitude.toString());
        data.put("longitude", longitude.toString());

        for (String deviceId : deviceIdList) {
            sendFcmNotificationToDevice(title, body, data, deviceId);
        }
    }

    private void sendFcmNotificationToDevice(String title, String body, Map<String, String> data, String deviceId) {
        Message message = Message.builder()
                .setToken(deviceId)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .putAllData(data)
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .build();

        LOGGER.info("[sendFcmNotificationToDevice] create message {}", new Gson().toJson(message));

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            LOGGER.info("[sendFcmNotificationToDevice] send message {}", response);
        } catch (FirebaseMessagingException e) {
            LOGGER.error("[sendFcmNotificationToDevice] exception {}", e.getMessage());
        }
    }
}
