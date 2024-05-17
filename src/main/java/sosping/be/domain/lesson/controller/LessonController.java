package sosping.be.domain.lesson.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sosping.be.domain.lesson.dto.LearnerJoinResponse;
import sosping.be.domain.lesson.dto.LessonRegisterRequest;
import sosping.be.domain.lesson.dto.LessonResponse;
import sosping.be.domain.lesson.service.LessonService;
import sosping.be.domain.member.domain.Member;
import sosping.be.global.aspect.LogMonitoring;
import sosping.be.global.util.BasicResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Tag(name = "Lesson")
@RequiredArgsConstructor
@RequestMapping("api/lesson")
public class LessonController {
    private final LessonService lessonService;
    // 강사 등록
    @LogMonitoring
    @Operation(summary = "강사 등록")
    @PostMapping("tutor")
    public ResponseEntity<BasicResponse> addTutorRole(@AuthenticationPrincipal Member member) {
        lessonService.addTutorRole(member);

        return ResponseEntity.ok(BasicResponse.of("SUCCESS"));
    }
    
    // 강의 등록
    @LogMonitoring
    @Operation(summary = "강의 등록", description = "바디에 `2024-05-19T04:00:00` 형식으로 조회")
    @PostMapping()
    public ResponseEntity<LessonResponse> registerLesson(@AuthenticationPrincipal Member member, @RequestBody LessonRegisterRequest request) {
        LessonResponse response = lessonService
                .registerLesson(member, request.lessonDateTime().toLocalDate(), request.lessonDateTime().toLocalTime());

        return ResponseEntity.ok(response);
    }

    // 수강생 들록
    @Operation(summary = "수강생 등록", description = "경로로 `lessonId`")
    @PostMapping("{lessonId}/learner")
    public ResponseEntity<LearnerJoinResponse> joinLesson(@AuthenticationPrincipal Member member, @PathVariable Long lessonId) {
        LearnerJoinResponse response = lessonService.joinLesson(member, lessonId);

        return ResponseEntity.ok(response);
    }

    // 강의 목록 전체 조회
    @LogMonitoring
    @Operation(summary = "날짜 기반 강의 목록 조회")
    @GetMapping("{date}")
    public ResponseEntity<List<LearnerJoinResponse>> getLessonList(@PathVariable(name = "date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate localDate = LocalDate.parse(date, formatter);

        List<LearnerJoinResponse> responses = lessonService.findAllLessonByLocalDate(localDate);

        return ResponseEntity.ok(responses);
    }
}
