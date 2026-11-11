package ro.ubm.paw.testeonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.testeonline.model.Utilizator;

import java.util.Optional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {

    Optional<Utilizator> findByEmailIgnoreCase(String email); // pentru login

    boolean existsByEmailIgnoreCase(String email); // pentru inregistrare: email duplicat?
}
