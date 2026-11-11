package ro.ubm.paw.testeonline.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.testeonline.model.Intrebare;

import java.util.List;

public interface IntrebareRepository extends JpaRepository<Intrebare, Long> {

    // intrebarile unui test, in ordine, cu variantele lor (un singur SELECT)
    @EntityGraph(attributePaths = "variante")
    List<Intrebare> findByTestIdOrderByOrdineAsc(Long testId);
}
