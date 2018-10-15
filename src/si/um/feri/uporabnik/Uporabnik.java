package si.um.feri.uporabnik;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import si.um.feri.paket.Paket;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Miha on 22/05/2017.
 */

@Entity
public class Uporabnik implements Serializable{

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private long id;

    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String email;

    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String naziv;

    private String opis;

    private String telefonskaStevilka;

    private Calendar datumPridruzitve;

    //NASLOV
    private String naslov;
    private int posta;

    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String kraj;

    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String ime;

    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    @Analyzer(definition = "analizatorpomeri")
    private String priimek;

    private String idApi;
    private int tip;

    @ManyToMany (cascade = CascadeType.ALL)
    private List<Uporabnik> priljubljeneKmetije;

    @ManyToMany (cascade = CascadeType.ALL)
    private List<Paket> paketi;


    public Uporabnik() {
    }

    public Uporabnik(UporabnikBuilder uporabnikBuilder){
        this.email = uporabnikBuilder.getEmail();
        this.idApi = uporabnikBuilder.getIdApi();
        this.ime = uporabnikBuilder.getIme();
        this.kraj = uporabnikBuilder.getKraj();
        this.priimek = uporabnikBuilder.getPriimek();
        this.naslov = uporabnikBuilder.getNaslov();
        this.naziv = uporabnikBuilder.getNaziv();
        this.opis = uporabnikBuilder.getOpis();
        this.posta = uporabnikBuilder.getPosta();
        this.tip = uporabnikBuilder.getTip();
        this.telefonskaStevilka = uporabnikBuilder.getTelefonskaStevilka();
        this.datumPridruzitve = uporabnikBuilder.getDatumKreacije();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public String getIdApi() {
        return idApi;
    }

    public void setIdApi(String idApi) {
        this.idApi = idApi;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTelefonskaStevilka() {
        return telefonskaStevilka;
    }

    public void setTelefonskaStevilka(String telefonskaStevilka) {
        this.telefonskaStevilka = telefonskaStevilka;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getPosta() {
        return posta;
    }

    public void setPosta(int posta) {
        this.posta = posta;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    @Override
    public String toString() {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        return g.toJson(this);
    }

    public Calendar getDatumPridruzitve() {
        return datumPridruzitve;
    }

    public void setDatumPridruzitve(Calendar datumPridruzitve) {
        this.datumPridruzitve = datumPridruzitve;
    }

    public List<Uporabnik> getPriljubljeneKmetije() {
        return priljubljeneKmetije;
    }

    public void setPriljubljeneKmetije(List<Uporabnik> priljubljeneKmetije) {this.priljubljeneKmetije = priljubljeneKmetije;}

    public List<Paket> getPaketi() {return paketi;}

    public void setPaketi(List<Paket> paketi) {this.paketi = paketi;}
}
