package sosping.be.domain.lesson.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record LessonResponse(
        Long lessonId,

        Integer maxCount,

        Integer count,

        LocalDate localDate,

        LocalTime localTime,

        boolean isFull,
        Long tutorId
) {
}
