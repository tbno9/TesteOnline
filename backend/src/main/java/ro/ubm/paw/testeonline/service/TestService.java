package ro.ubm.paw.testeonline.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubm.paw.testeonline.model.*;
import ro.ubm.paw.testeonline.repository.*;
import ro.ubm.paw.testeonline.web.dto.*;

import java.util.List;

/** Logica pentru teste si intrebari. Intoarce DTO-uri, nu entitati. */
@Service
@Transactional
public class TestService {

    private final TestRepository testRepo;
    private final IntrebareRepository intrebareRepo;
    private final VariantaRepository variantaRepo;
    private final IncercareRepository incercareRepo;
    private final UtilizatorRepository utilizatorRepo;

    public TestService(TestRepository testRepo, IntrebareRepository intrebareRepo,
                       VariantaRepository variantaRepo, IncercareRepository incercareRepo,
                       UtilizatorRepository utilizatorRepo) {
        this.testRepo = testRepo;
        this.intrebareRepo = intrebareRepo;
        this.variantaRepo = variantaRepo;
        this.incercareRepo = incercareRepo;
        this.utilizatorRepo = utilizatorRepo;
    }

    // ---------- teste ----------

    @Transactional(readOnly = true)
    public List<TestSumarDto> lista(String cautare) {
        List<Test> teste = (cautare == null || cautare.isBlank())
                ? testRepo.findAllByOrderByTitluAsc()
                : testRepo.findByTitluContainingIgnoreCaseOrderByTitluAsc(cautare.trim());
        return teste.stream().map(TestSumarDto::from).toList();
    }

    @Transactional(readOnly = true)
    public TestDetaliuDto detaliu(Long id) {
        Test test = gasesteTest(id);
        return TestDetaliuDto.from(test, intrebareRepo.findByTestIdOrderByOrdineAsc(id));
    }

    public TestDetaliuDto creeaza(TestRequest req) {
        Utilizator autor = utilizatorRepo.findById(req.autorId())
                .orElseThrow(() -> new CerereInvalidaException("Autorul " + req.autorId() + " nu există"));
        if (autor.getRol() != Rol.PROFESOR) {
            throw new CerereInvalidaException("Doar un profesor poate crea teste");
        }
        Test test = testRepo.save(new Test(req.titlu(), req.descriere(), req.domeniu(),
                req.durataMinute(), autor));
        return TestDetaliuDto.from(test, List.of());
    }

    // titlul/durata se pot schimba si dupa ce testul a fost rezolvat; autorul nu se schimba
    public TestDetaliuDto actualizeaza(Long id, TestRequest req) {
        Test test = gasesteTest(id);
        test.setTitlu(req.titlu());
        test.setDescriere(req.descriere());
        test.setDomeniu(req.domeniu());
        test.setDurataMinute(req.durataMinute());
        return TestDetaliuDto.from(test, intrebareRepo.findByTestIdOrderByOrdineAsc(id));
    }

    public void sterge(Long id) {
        Test test = gasesteTest(id);
        verificaNeblocat(id);
        testRepo.delete(test); // intrebarile si variantele se sterg prin ON DELETE CASCADE din V1
    }

    // ---------- intrebari ----------

    public IntrebareDto adaugaIntrebare(Long testId, IntrebareRequest req) {
        Test test = gasesteTest(testId);
        verificaNeblocat(testId);
        verificaVariante(req);
        List<Intrebare> existente = test.getIntrebari(); // deja ordonate dupa "ordine"
        int ordine = existente.isEmpty() ? 1 : existente.get(existente.size() - 1).getOrdine() + 1;
        Intrebare intrebare = intrebareRepo.save(new Intrebare(req.text(), req.punctaj(), ordine, test));
        salveazaVariante(intrebare, req);
        return IntrebareDto.from(intrebare);
    }

    public IntrebareDto actualizeazaIntrebare(Long intrebareId, IntrebareRequest req) {
        Intrebare intrebare = gasesteIntrebare(intrebareId);
        verificaNeblocat(intrebare.getTest().getId());
        verificaVariante(req);
        intrebare.setText(req.text());
        intrebare.setPunctaj(req.punctaj());
        // variantele vechi se inlocuiesc complet cu cele noi
        variantaRepo.deleteAll(intrebare.getVariante());
        intrebare.getVariante().clear();
        salveazaVariante(intrebare, req);
        return IntrebareDto.from(intrebare);
    }

    public void stergeIntrebare(Long intrebareId) {
        Intrebare intrebare = gasesteIntrebare(intrebareId);
        verificaNeblocat(intrebare.getTest().getId());
        intrebareRepo.delete(intrebare);
    }

    // ---------- ajutatoare ----------

    private Test gasesteTest(Long id) {
        return testRepo.findById(id)
                .orElseThrow(() -> new ResursaNegasitaException("Testul " + id + " nu există"));
    }

    private Intrebare gasesteIntrebare(Long id) {
        return intrebareRepo.findById(id)
                .orElseThrow(() -> new ResursaNegasitaException("Întrebarea " + id + " nu există"));
    }

    // regula: dupa prima incercare, testul e "inghetat"
    private void verificaNeblocat(Long testId) {
        if (incercareRepo.existsByTestId(testId)) {
            throw new ConflictException("Testul a fost deja rezolvat de studenți; "
                    + "întrebările nu mai pot fi modificate, iar testul nu poate fi șters");
        }
    }

    private void verificaVariante(IntrebareRequest req) {
        if (req.variante().stream().noneMatch(VariantaRequest::corecta)) {
            throw new CerereInvalidaException("Întrebarea trebuie să aibă cel puțin o variantă corectă");
        }
    }

    private void salveazaVariante(Intrebare intrebare, IntrebareRequest req) {
        for (VariantaRequest v : req.variante()) {
            intrebare.getVariante().add(variantaRepo.save(new Varianta(v.text(), v.corecta(), intrebare)));
        }
    }
}
