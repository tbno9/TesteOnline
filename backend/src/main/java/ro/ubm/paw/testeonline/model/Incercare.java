package ro.ubm.paw.testeonline.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/** O rezolvare a unui test de catre un student. */
@Entity
@Table(name = "incercare")
public class Incercare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id")
    private Utilizator student;

    @Column(name = "inceputa_la", nullable = false, updatable = false)
    private LocalDateTime inceputaLa;

    @Column(name = "terminata_la")
    private LocalDateTime terminataLa; // null = testul este inca in lucru

    @Column(precision = 6, scale = 2)
    private BigDecimal scor; // null pana la terminare

    // Relatie N-N prin tabela de legatura raspuns_ales: variantele bifate de student
    @ManyToMany
    @JoinTable(name = "raspuns_ales",
            joinColumns = @JoinColumn(name = "incercare_id"),
            inverseJoinColumns = @JoinColumn(name = "varianta_id"))
    private Set<Varianta> raspunsuri = new HashSet<>();

    protected Incercare() { }

    public Incercare(Test test, Utilizator student) {
        this.test = test;
        this.student = student;
    }

    @PrePersist
    void laCreare() {
        inceputaLa = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Test getTest() { return test; }
    public Utilizator getStudent() { return student; }
    public LocalDateTime getInceputaLa() { return inceputaLa; }
    public LocalDateTime getTerminataLa() { return terminataLa; }
    public BigDecimal getScor() { return scor; }
    public Set<Varianta> getRaspunsuri() { return raspunsuri; }

    public void setTerminataLa(LocalDateTime terminataLa) { this.terminataLa = terminataLa; }
    public void setScor(BigDecimal scor) { this.scor = scor; }
}
