package ro.ubm.paw.testeonline.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    /** Regulile de acces pentru tot API-ul. Se citesc de sus in jos; prima potrivire castiga. */
    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http,
                                           JwtAuthenticationConverter jwtConverter,
                                           ProblemDetailSecurityHandler erori) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers("/api/auth/eu").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/teste").hasAnyRole("PROFESOR", "STUDENT")
                .anyRequest().hasRole("PROFESOR"))
            .oauth2ResourceServer(rs -> rs
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter))
                .authenticationEntryPoint(erori)   // token invalid / expirat -> 401
                .accessDeniedHandler(erori))       // rol insuficient -> 403
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(erori)   // fara token deloc -> 401
                .accessDeniedHandler(erori))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(csrf -> csrf.disable());         // fara cookie-uri, deci fara CSRF
        return http.build();
    }

    /** Compara parola introdusa cu hash-ul din BD; prefixul {bcrypt} alege algoritmul. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /** Cheia secreta din application.properties, transformata pentru HMAC-SHA256. */
    @Bean
    public SecretKey jwtSecretKey(@Value("${app.jwt.secret}") String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("app.jwt.secret: minim 32 de octeti");
        }
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    /** Semneaza token-urile la login. */
    @Bean
    public JwtEncoder jwtEncoder(SecretKey key) {
        return NimbusJwtEncoder.withSecretKey(key).algorithm(MacAlgorithm.HS256).build();
    }

    /** Verifica semnatura si expirarea ("exp") la fiecare cerere cu token. */
    @Bean
    public JwtDecoder jwtDecoder(SecretKey key) {
        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
    }

    /** Claim-ul "roles": ["PROFESOR"] din token devine autoritatea ROLE_PROFESOR. */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter roluri = new JwtGrantedAuthoritiesConverter();
        roluri.setAuthoritiesClaimName("roles");
        roluri.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(roluri);
        return converter;
    }
}
