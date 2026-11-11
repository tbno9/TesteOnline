package ro.ubm.paw.testeonline.web.dto;

import ro.ubm.paw.testeonline.model.Varianta;

/** Contine "corecta": se trimite doar profesorului, nu studentului. */
public record VariantaDto(Long id, String text, boolean corecta) {

    public static VariantaDto from(Varianta v) {
        return new VariantaDto(v.getId(), v.getText(), v.isCorecta());
    }
}
