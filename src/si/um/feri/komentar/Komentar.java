package si.um.feri.komentar;

import javax.persistence.*;

import org.hibernate.search.annotations.IndexedEmbedded;
import si.um.feri.uporabnik.*;
import java.io.Serializable;

/**
 * Created by Klemen on 7. 06. 2017.
 */

@Entity
public class Komentar implements Serializable{

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private long id;

    private String komentar;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Uporabnik uporabnik;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Uporabnik kmetija;

    private int ocena;

    public Komentar(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Uporabnik getKmetija() {
        return kmetija;
    }

    public void setKmetija(Uporabnik kmetija) {
        this.kmetija = kmetija;
    }

    public int getOcena() {  return ocena;  }

    public void setOcena(int ocena) {   this.ocena = ocena;   }
}
