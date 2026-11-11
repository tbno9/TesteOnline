package ro.ubm.paw.testeonline.model;

import jakarta.persistence.*;

@Entity
@Table(name = "varianta")
public class Varianta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String text;

    @Column(nullable = false)
    private boolean corecta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "intrebare_id")
    private Intrebare intrebare;

    protected Varianta() { }

    public Varianta(String text, boolean corecta, Intrebare intrebare) {
        this.text = text;
        this.corecta = corecta;
        this.intrebare = intrebare;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public boolean isCorecta() { return corecta; }
    public Intrebare getIntrebare() { return intrebare; }

    public void setText(String text) { this.text = text; }
    public void setCorecta(boolean corecta) { this.corecta = corecta; }
}
