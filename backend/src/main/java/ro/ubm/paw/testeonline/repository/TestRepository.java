package ro.ubm.paw.testeonline.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.testeonline.model.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    // @EntityGraph aduce si autorul in acelasi SELECT (evita problema N+1)
    @EntityGraph(attributePaths = "autor")
    List<Test> findAllByOrderByTitluAsc();

    @EntityGraph(attributePaths = "autor")
    List<Test> findByTitluContainingIgnoreCaseOrderByTitluAsc(String text);

    // testele create de un anumit profesor
    @EntityGraph(attributePaths = "autor")
    List<Test> findByAutorIdOrderByTitluAsc(Long autorId);
}
