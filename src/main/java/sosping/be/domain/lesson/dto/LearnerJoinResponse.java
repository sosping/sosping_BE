package sosping.be.domain.lesson.dto;

public record LearnerJoinResponse(
        LessonResponse lessonResponse,
        String tutorName
) {
}
