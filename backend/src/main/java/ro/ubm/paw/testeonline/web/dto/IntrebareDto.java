package ro.ubm.paw.testeonline.web.dto;

import ro.ubm.paw.testeonline.model.Intrebare;

import java.util.List;

public record IntrebareDto(Long id, String text, int punctaj, int ordine,
                           List<VariantaDto> variante) {

    public static IntrebareDto from(Intrebare i) {
        return new IntrebareDto(i.getId(), i.getText(), i.getPunctaj(), i.getOrdine(),
                i.getVariante().stream().map(VariantaDto::from).toList());
    }
}
