package sosping.be.domain.lesson.converter;

import sosping.be.domain.lesson.domain.Lesson;
import sosping.be.domain.lesson.dto.LessonResponse;

public class LessonConverter {
    public static LessonResponse convert(Lesson lesson) {
        return new LessonResponse(
                lesson.getLessonId(),
                lesson.getMaxCount(),
                lesson.getCount(),
                lesson.getLessonDate(),
                lesson.getLessonTime(),
                lesson.isFull(),
                lesson.getMember().getMemberId());
    }
}
