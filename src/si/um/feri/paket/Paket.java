package si.um.feri.paket;

import si.um.feri.produkt.Produkt;
import si.um.feri.uporabnik.Uporabnik;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by David on 2.6.2017.
 */
@Entity
public class Paket implements Serializable {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String naziv;

    private double cena;

    private String opis;

    @ManyToMany (cascade = CascadeType.ALL)
    private List<Uporabnik> uporabnik;

    @ManyToOne (cascade = CascadeType.ALL)
    private Uporabnik kmetija;

    public Paket() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public List<Uporabnik> getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(List<Uporabnik> uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Uporabnik getKmetija() {
        return kmetija;
    }

    public void setKmetija(Uporabnik kmetija) {
        this.kmetija = kmetija;
    }

    @Override
    public String toString() {
        return "Paket{" +
                "naziv='" + naziv + '\'' +
                '}';
    }
}
