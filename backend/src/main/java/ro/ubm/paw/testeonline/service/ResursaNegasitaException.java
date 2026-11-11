package ro.ubm.paw.testeonline.service;

/** Resursa ceruta nu exista. ApiExceptionHandler o transforma in 404 Not Found. */
public class ResursaNegasitaException extends RuntimeException {
    public ResursaNegasitaException(String mesaj) { super(mesaj); }
}
