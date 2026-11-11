package ro.ubm.paw.testeonline.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.testeonline.model.Incercare;

import java.util.List;

public interface IncercareRepository extends JpaRepository<Incercare, Long> {

    // istoricul unui student: cele mai noi primele
    @EntityGraph(attributePaths = "test")
    List<Incercare> findByStudentIdOrderByInceputaLaDesc(Long studentId);

    // rezultatele la un test, pentru profesor
    @EntityGraph(attributePaths = "student")
    List<Incercare> findByTestIdOrderByInceputaLaDesc(Long testId);
}
