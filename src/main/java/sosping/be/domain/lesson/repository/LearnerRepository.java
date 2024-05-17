package sosping.be.domain.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sosping.be.domain.lesson.domain.Learner;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
}
