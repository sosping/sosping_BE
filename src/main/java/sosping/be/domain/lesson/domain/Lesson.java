package sosping.be.domain.lesson.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import sosping.be.domain.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter @ToString(exclude = "learners")
@Entity
public class Lesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    private Integer maxCount;

    private Integer count;

    private LocalDate lessonDate;

    private LocalTime lessonTime;

    private boolean isFull;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Learner> learners= new ArrayList<>();

    public void joinLearner(Learner learner) {
        learners.add(learner);
    }

    public void increaseCount() {
        this.count += 1;
        if (this.count.equals(this.maxCount)) {
            this.isFull = true;
        }
    }

    @Builder
    public Lesson(Long lessonId, Integer maxCount, Integer count, LocalDate lessonDate, LocalTime lessonTime, boolean isFull, Member member) {
        this.lessonId = lessonId;
        this.maxCount = maxCount;
        this.count = count;
        this.lessonDate = lessonDate;
        this.lessonTime = lessonTime;
        this.isFull = isFull;
        this.member = member;
    }

    public Lesson() {

    }
}
