package ro.ubm.paw.testeonline.service;

/** Operatia intra in conflict cu datele existente (ex.: stergerea unui test deja rezolvat). Devine 409 Conflict. */
public class ConflictException extends RuntimeException {
    public ConflictException(String mesaj) { super(mesaj); }
}
