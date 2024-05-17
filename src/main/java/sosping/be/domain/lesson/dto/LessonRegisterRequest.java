package sosping.be.domain.lesson.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LessonRegisterRequest(
        LocalDateTime lessonDateTime
) {
}
