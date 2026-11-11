package ro.ubm.paw.testeonline.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intrebare")
public class Intrebare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String text;

    @Column(nullable = false)
    private int punctaj;

    @Column(nullable = false)
    private int ordine; // pozitia intrebarii in test: 1, 2, 3...

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "intrebare")
    private List<Varianta> variante = new ArrayList<>();

    protected Intrebare() { }

    public Intrebare(String text, int punctaj, int ordine, Test test) {
        this.text = text;
        this.punctaj = punctaj;
        this.ordine = ordine;
        this.test = test;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public int getPunctaj() { return punctaj; }
    public int getOrdine() { return ordine; }
    public Test getTest() { return test; }
    public List<Varianta> getVariante() { return variante; }

    public void setText(String text) { this.text = text; }
    public void setPunctaj(int punctaj) { this.punctaj = punctaj; }
    public void setOrdine(int ordine) { this.ordine = ordine; }
}
