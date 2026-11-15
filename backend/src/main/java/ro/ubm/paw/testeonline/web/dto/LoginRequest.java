package ro.ubm.paw.testeonline.web.dto;

import jakarta.validation.constraints.NotBlank;

/** Corpul cererii POST /api/auth/login. */
public record LoginRequest(
        @NotBlank(message = "Emailul este obligatoriu") String email,
        @NotBlank(message = "Parola este obligatorie") String parola) { }
