package ro.ubm.paw.testeonline.model;

import jakarta.persistence.*;

@Entity
@Table(name = "utilizator")
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nume;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // contine hash-ul BCrypt, niciodata parola in clar
    @Column(nullable = false)
    private String parola;

    @Enumerated(EnumType.STRING) // salveaza "PROFESOR", nu pozitia 0
    @Column(nullable = false, length = 20)
    private Rol rol;

    protected Utilizator() { } // cerut de JPA

    public Utilizator(String nume, String email, String parola, Rol rol) {
        this.nume = nume;
        this.email = email;
        this.parola = parola;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public String getNume() { return nume; }
    public String getEmail() { return email; }
    public String getParola() { return parola; }
    public Rol getRol() { return rol; }

    public void setNume(String nume) { this.nume = nume; }
    public void setParola(String parola) { this.parola = parola; }
}
