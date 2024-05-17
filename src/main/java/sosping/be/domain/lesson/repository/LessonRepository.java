package sosping.be.domain.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sosping.be.domain.lesson.domain.Lesson;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findLessonByLessonDateIs(LocalDate localDate);
}
