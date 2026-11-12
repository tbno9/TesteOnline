package ro.ubm.paw.testeonline.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.ubm.paw.testeonline.service.TestService;
import ro.ubm.paw.testeonline.web.dto.*;

import java.util.List;

/** API REST pentru teste si intrebari. Toata logica e in TestService. */
@RestController
@RequestMapping("/api")
public class TestApiController {

    private final TestService service;

    public TestApiController(TestService service) {
        this.service = service;
    }

    // ---------- teste ----------

    @GetMapping("/teste")
    public List<TestSumarDto> lista(@RequestParam(required = false) String cautare) {
        return service.lista(cautare);
    }

    @GetMapping("/teste/{id}")
    public TestDetaliuDto detaliu(@PathVariable Long id) {
        return service.detaliu(id);
    }

    @PostMapping("/teste")
    @ResponseStatus(HttpStatus.CREATED)
    public TestDetaliuDto creeaza(@Valid @RequestBody TestRequest req) {
        return service.creeaza(req);
    }

    @PutMapping("/teste/{id}")
    public TestDetaliuDto actualizeaza(@PathVariable Long id, @Valid @RequestBody TestRequest req) {
        return service.actualizeaza(id, req);
    }

    @DeleteMapping("/teste/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sterge(@PathVariable Long id) {
        service.sterge(id);
    }

    // ---------- intrebari ----------

    @PostMapping("/teste/{testId}/intrebari")
    @ResponseStatus(HttpStatus.CREATED)
    public IntrebareDto adaugaIntrebare(@PathVariable Long testId, @Valid @RequestBody IntrebareRequest req) {
        return service.adaugaIntrebare(testId, req);
    }

    @PutMapping("/intrebari/{id}")
    public IntrebareDto actualizeazaIntrebare(@PathVariable Long id, @Valid @RequestBody IntrebareRequest req) {
        return service.actualizeazaIntrebare(id, req);
    }

    @DeleteMapping("/intrebari/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stergeIntrebare(@PathVariable Long id) {
        service.stergeIntrebare(id);
    }
}
