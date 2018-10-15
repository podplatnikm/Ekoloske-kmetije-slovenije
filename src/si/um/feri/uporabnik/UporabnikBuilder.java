package si.um.feri.uporabnik;

import java.util.Calendar;

public class UporabnikBuilder {

    private String email;
    private String naziv;
    private String telefonskaStevilka;
    private String opis;
    private String naslov;
    private int posta;
    private String kraj;
    private String ime;
    private String priimek;
    private String idApi;
    private int tip;
    private Calendar datumKreacije;

    public String getEmail() {
        return email;
    }

    public UporabnikBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNaziv() {
        return naziv;
    }

    public UporabnikBuilder setNaziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public String getTelefonskaStevilka() {
        return telefonskaStevilka;
    }

    public UporabnikBuilder setTelefonskaStevilka(String telefonskaStevilka) {
        this.telefonskaStevilka = telefonskaStevilka;
        return this;
    }

    public String getOpis() {
        return opis;
    }

    public UporabnikBuilder setOpis(String opis) {
        this.opis = opis;
        return this;
    }

    public String getNaslov() {
        return naslov;
    }

    public UporabnikBuilder setNaslov(String naslov) {
        this.naslov = naslov;
        return this;
    }

    public int getPosta() {
        return posta;
    }

    public UporabnikBuilder setPosta(int posta) {
        this.posta = posta;
        return this;
    }

    public String getKraj() {
        return kraj;
    }

    public UporabnikBuilder setKraj(String kraj) {
        this.kraj = kraj;
        return this;
    }

    public String getIme() {
        return ime;
    }

    public UporabnikBuilder setIme(String ime) {
        this.ime = ime;
        return this;
    }

    public String getPriimek() {
        return priimek;
    }

    public UporabnikBuilder setPriimek(String priimek) {
        this.priimek = priimek;
        return this;
    }

    public String getIdApi() {
        return idApi;
    }

    public UporabnikBuilder setIdApi(String idApi) {
        this.idApi = idApi;
        return this;
    }

    public int getTip() {
        return tip;
    }

    public UporabnikBuilder setTip(int tip) {
        this.tip = tip;
        return this;
    }

    public Calendar getDatumKreacije() {
        return datumKreacije;
    }

    public UporabnikBuilder setDatumKreacije(Calendar datumKreacije) {
        this.datumKreacije = datumKreacije;
        return this;
    }

    public Uporabnik createUporabnik() {
        return new Uporabnik(this);
    }


}