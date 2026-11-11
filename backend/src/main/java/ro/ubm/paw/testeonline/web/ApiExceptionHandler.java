package ro.ubm.paw.testeonline.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.ubm.paw.testeonline.service.CerereInvalidaException;
import ro.ubm.paw.testeonline.service.ConflictException;
import ro.ubm.paw.testeonline.service.ResursaNegasitaException;

/**
 * Transforma exceptiile aruncate din controllere in raspunsuri JSON ProblemDetail.
 * Clasa parinte trateaza deja erorile standard Spring (JSON invalid, @Valid etc.).
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResursaNegasitaException.class)
    public ProblemDetail negasit(ResursaNegasitaException ex) {
        return problema(HttpStatus.NOT_FOUND, "Resursă negăsită", ex.getMessage());
    }

    @ExceptionHandler(CerereInvalidaException.class)
    public ProblemDetail invalid(CerereInvalidaException ex) {
        return problema(HttpStatus.BAD_REQUEST, "Cerere invalidă", ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail conflict(ConflictException ex) {
        return problema(HttpStatus.CONFLICT, "Conflict", ex.getMessage());
    }

    private static ProblemDetail problema(HttpStatus status, String titlu, String detaliu) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detaliu);
        pd.setTitle(titlu);
        return pd;
    }
}
