package si.um.feri.produkt;

import si.um.feri.uporabnik.Uporabnik;

import java.util.Calendar;
import java.util.List;

public class ProduktBuilder {

    private String naziv;
    private double cena;
    private int kolicina;
    private String opis;
    private String kategorija;
    private Uporabnik kmetija;
    private List<Znacka> znacke;
    private Calendar datumPridruzitve;
    private String nizOznak;


    public ProduktBuilder setNaziv(String naziv) {
        this.naziv = naziv;
        return this;
    }


    public ProduktBuilder setCena(double cena) {
        this.cena = cena;
        return this;
    }

    public ProduktBuilder setKolicina(int kolicina) {
        this.kolicina = kolicina;
        return this;
    }


    public ProduktBuilder setOpis(String opis) {
        this.opis = opis;
        return this;
    }


    public ProduktBuilder setKategorija(String kategorija) {
        this.kategorija = kategorija;
        return this;
    }


    public ProduktBuilder setKmetija(Uporabnik kmetija) {
        this.kmetija = kmetija;
        return this;
    }

    public ProduktBuilder setZnacke(List<Znacka> znacke) {
        this.znacke = znacke;
        return this;
    }

    public ProduktBuilder setDatumPridruzitve(Calendar datumPridruzitve) {
        this.datumPridruzitve = datumPridruzitve;
        return this;
    }

    public Produkt createProdukt() {
        return new Produkt(this);
    }


    public String getNaziv() {
        return naziv;
    }

    public double getCena() {
        return cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public String getOpis() {
        return opis;
    }

    public String getKategorija() {
        return kategorija;
    }

    public Uporabnik getKmetija() {
        return kmetija;
    }

    public List<Znacka> getZnacke() {
        return znacke;
    }

    public Calendar getDatumPridruzitve() {
        return datumPridruzitve;
    }

    public String getNizOznak() {
        return nizOznak;
    }

    public ProduktBuilder setNizOznak(String nizOznak) {
        this.nizOznak = nizOznak;
        return this;
    }
}