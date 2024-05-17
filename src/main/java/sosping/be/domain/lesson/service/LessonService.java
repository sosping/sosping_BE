package sosping.be.domain.lesson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sosping.be.domain.lesson.converter.LessonConverter;
import sosping.be.domain.lesson.domain.Learner;
import sosping.be.domain.lesson.domain.Lesson;
import sosping.be.domain.lesson.dto.LessonResponse;
import sosping.be.domain.lesson.repository.LearnerRepository;
import sosping.be.domain.lesson.repository.LessonRepository;
import sosping.be.domain.member.domain.Member;
import sosping.be.domain.member.service.MemberService;
import sosping.be.global.exception.ErrorCode;
import sosping.be.global.exception.domain.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final MemberService memberService;
    private final LessonRepository lessonRepository;
    private final LearnerRepository learnerRepository;

    public void addTutorRole(Member member) {
        memberService.addTutorRole(member.getMemberId());
    }

    public LessonResponse registerLesson(Member member, LocalDate date, LocalTime time) {
        Lesson lesson = Lesson.builder()
                .maxCount(20)
                .count(0)
                .isFull(false)
                .lessonDate(date)
                .lessonTime(time)
                .member(member)
                .build();

        lesson = lessonRepository.save(lesson);
        return LessonConverter.convert(lesson);
    }

    @Transactional
    public LessonResponse joinLesson(Member member, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STAGE_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (lesson.getCount() >= lesson.getMaxCount()) {
            throw new BusinessException(ErrorCode.UNKNOWN, HttpStatus.BAD_REQUEST);
        }

        // 기존 학습자 리스트에 멤버가 있는지 확인
        for (Learner learner : lesson.getLearners()) {
            if (learner.getMember().equals(member)) {
                throw new BusinessException(ErrorCode.UNKNOWN, HttpStatus.BAD_REQUEST);
            }
        }

        Learner learner = Learner.builder()
                .lesson(lesson)
                .member(member)
                .build();

        lesson.getLearners().add(learner);
        lesson.increaseCount();

        learnerRepository.save(learner);
        return LessonConverter.convert(lessonRepository.save(lesson));
    }

    @Transactional
    public List<LessonResponse> findAllLessonByLocalDate(LocalDate localDate) {
        List<LessonResponse> responses = lessonRepository.findLessonByLessonDateIs(localDate).stream()
                .map(LessonConverter::convert)
                .toList();

        return responses;
    }


}
