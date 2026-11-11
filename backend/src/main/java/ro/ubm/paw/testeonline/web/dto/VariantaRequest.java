package ro.ubm.paw.testeonline.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** O varianta de raspuns din IntrebareRequest. */
public record VariantaRequest(
        @NotBlank(message = "Textul variantei este obligatoriu")
        @Size(max = 500, message = "Varianta are cel mult 500 de caractere")
        String text,

        boolean corecta
) { }
