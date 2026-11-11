package ro.ubm.paw.testeonline.service;

/** Datele incalca o regula a aplicatiei (ex.: intrebare fara varianta corecta). Devine 400 Bad Request. */
public class CerereInvalidaException extends RuntimeException {
    public CerereInvalidaException(String mesaj) { super(mesaj); }
}
