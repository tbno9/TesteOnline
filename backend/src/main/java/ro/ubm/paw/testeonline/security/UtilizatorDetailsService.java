package ro.ubm.paw.testeonline.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubm.paw.testeonline.repository.UtilizatorRepository;

/**
 * Puntea dintre Spring Security si tabela "utilizator".
 * La login, Spring da emailul introdus; intoarcem hash-ul parolei si rolul,
 * iar comparatia parolei o face Spring, cu PasswordEncoder.
 */
@Service
public class UtilizatorDetailsService implements UserDetailsService {

    private final UtilizatorRepository utilizatori;

    public UtilizatorDetailsService(UtilizatorRepository utilizatori) {
        this.utilizatori = utilizatori;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        return utilizatori.findByEmailIgnoreCase(email)
                .map(u -> User.withUsername(u.getEmail())
                        .password(u.getParola())       // hash-ul {bcrypt}$2a$10$...
                        .roles(u.getRol().name())      // PROFESOR -> ROLE_PROFESOR
                        .build())
                // Spring transforma exceptia in acelasi mesaj ca la parola gresita,
                // ca un atacator sa nu afle ce emailuri au cont
                .orElseThrow(() -> new UsernameNotFoundException("Utilizator inexistent"));
    }
}
