package sosping.be.domain.lesson.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import sosping.be.domain.member.domain.Member;

@Getter @ToString()
@Entity
@Table(name = "learner")
public class Learner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learner_id")
    private Long learnerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Builder
    public Learner(Long learnerId, Member member, Lesson lesson) {
        this.learnerId = learnerId;
        this.member = member;
        this.lesson = lesson;
    }

    public Learner() {
    }
}
