package sosping.be.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sosping.be.domain.member.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(Long memberId);

    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT m FROM Member m WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(m.latitude)) * cos(radians(m.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.latitude)))) < 1")
    List<Member> findMembersWithinDistance(@Param("latitude") Double latitude, @Param("longitude") Double longitude);

    Optional<Member> findByName(String name);
}
