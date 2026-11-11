package ro.ubm.paw.testeonline.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** JSON-ul primit la POST si PUT /api/teste. */
public record TestRequest(
        @NotBlank(message = "Titlul este obligatoriu")
        @Size(max = 150, message = "Titlul are cel mult 150 de caractere")
        String titlu,

        @Size(max = 1000, message = "Descrierea are cel mult 1000 de caractere")
        String descriere,

        @NotBlank(message = "Domeniul este obligatoriu")
        @Size(max = 100, message = "Domeniul are cel mult 100 de caractere")
        String domeniu,

        @NotNull(message = "Durata este obligatorie")
        @Min(value = 1, message = "Durata trebuie să fie de cel puțin 1 minut")
        Integer durataMinute,

        // shortcut: temporar, pana la securitate; atunci autorul va fi utilizatorul logat
        @NotNull(message = "Autorul este obligatoriu")
        Long autorId
) { }
