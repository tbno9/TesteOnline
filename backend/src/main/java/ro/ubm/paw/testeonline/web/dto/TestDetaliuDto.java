package ro.ubm.paw.testeonline.web.dto;

import ro.ubm.paw.testeonline.model.Intrebare;
import ro.ubm.paw.testeonline.model.Test;

import java.util.List;

/** Un test complet, cu intrebarile si variantele lor (pentru profesor). */
public record TestDetaliuDto(Long id, String titlu, String descriere, String domeniu,
                             int durataMinute, String autorNume,
                             List<IntrebareDto> intrebari) {

    public static TestDetaliuDto from(Test t, List<Intrebare> intrebari) {
        return new TestDetaliuDto(t.getId(), t.getTitlu(), t.getDescriere(), t.getDomeniu(),
                t.getDurataMinute(), t.getAutor().getNume(),
                intrebari.stream().map(IntrebareDto::from).toList());
    }
}
