package ro.ubm.paw.testeonline.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/** JSON-ul primit la POST /api/teste/{id}/intrebari si PUT /api/intrebari/{id}. */
public record IntrebareRequest(
        @NotBlank(message = "Textul întrebării este obligatoriu")
        @Size(max = 1000, message = "Textul are cel mult 1000 de caractere")
        String text,

        @NotNull(message = "Punctajul este obligatoriu")
        @Min(value = 1, message = "Punctajul trebuie să fie cel puțin 1")
        Integer punctaj,

        @NotNull(message = "Variantele sunt obligatorii")
        @Size(min = 2, message = "O întrebare grilă are cel puțin 2 variante")
        List<@Valid VariantaRequest> variante
) { }
