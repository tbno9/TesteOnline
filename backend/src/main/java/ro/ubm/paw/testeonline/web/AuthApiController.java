package ro.ubm.paw.testeonline.web;

import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ro.ubm.paw.testeonline.security.TokenService;
import ro.ubm.paw.testeonline.web.dto.LoginRequest;
import ro.ubm.paw.testeonline.web.dto.TokenResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthApiController(AuthenticationConfiguration config,
                             TokenService tokenService) throws Exception {
        // managerul construit de Spring din UtilizatorDetailsService + PasswordEncoder
        this.authenticationManager = config.getAuthenticationManager();
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest req) {
        // parola gresita / email inexistent -> BadCredentialsException -> 401 (ApiExceptionHandler)
        Authentication auth = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(req.email(), req.parola()));
        return new TokenResponse(tokenService.genereaza(auth), "Bearer",
                tokenService.valabilitateSecunde());
    }

    @GetMapping("/eu")
    public Map<String, Object> eu(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("email", jwt.getSubject(),
                      "roluri", jwt.getClaimAsStringList("roles"));
    }
}
