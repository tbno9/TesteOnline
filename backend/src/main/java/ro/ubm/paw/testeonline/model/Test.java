package ro.ubm.paw.testeonline.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titlu;

    @Column(length = 1000)
    private String descriere;

    @Column(nullable = false, length = 100)
    private String domeniu;

    @Column(name = "durata_minute", nullable = false)
    private int durataMinute;

    // multe teste -> un autor (profesorul); aici este cheia straina
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id")
    private Utilizator autor;

    // partea inversa: cheia straina test_id este in tabela intrebare
    @OneToMany(mappedBy = "test")
    @OrderBy("ordine ASC")
    private List<Intrebare> intrebari = new ArrayList<>();

    protected Test() { }

    public Test(String titlu, String descriere, String domeniu, int durataMinute, Utilizator autor) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.domeniu = domeniu;
        this.durataMinute = durataMinute;
        this.autor = autor;
    }

    public Long getId() { return id; }
    public String getTitlu() { return titlu; }
    public String getDescriere() { return descriere; }
    public String getDomeniu() { return domeniu; }
    public int getDurataMinute() { return durataMinute; }
    public Utilizator getAutor() { return autor; }
    public List<Intrebare> getIntrebari() { return intrebari; }

    public void setTitlu(String titlu) { this.titlu = titlu; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public void setDomeniu(String domeniu) { this.domeniu = domeniu; }
    public void setDurataMinute(int durataMinute) { this.durataMinute = durataMinute; }
}
