package ro.ubm.paw.testeonline.web.dto;

import ro.ubm.paw.testeonline.model.Test;

/** Un rand din lista de teste. */
public record TestSumarDto(Long id, String titlu, String domeniu,
                           int durataMinute, String autorNume) {

    public static TestSumarDto from(Test t) {
        return new TestSumarDto(t.getId(), t.getTitlu(), t.getDomeniu(),
                t.getDurataMinute(), t.getAutor().getNume());
    }
}
