package si.um.feri.uporabnik;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Klemen on 26. 05. 2017.
 */
@ManagedBean(name = "uporabnikBean")
@SessionScoped
public class UporabnikBean implements Serializable {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    UporabnikVmesnik uporabnikDao;
    Uporabnik vpisanUporabnik;
    Uporabnik novUporabnik = new UporabnikBuilder().createUporabnik();
    Uporabnik uporabnik = new UporabnikBuilder().createUporabnik();
    Uporabnik izbranaKmetija = new UporabnikBuilder().createUporabnik();
    List<String> naslovi = new ArrayList<>();
    List<String> nazivi = new ArrayList<>();
    List<String> idji = new ArrayList<>();
    List<String> kraji = new ArrayList<>();
    List<Uporabnik> priljubljeneKmetije = new ArrayList<>();

    private long idIzbraneKmetije;
    private long idVpisanegaUporabnika;
    private String naravnaDomacaStran = "#";


    public void pridobiZaMapo() {
        try {
            naslovi = uporabnikDao.pridobiVseNaslove();
            nazivi = uporabnikDao.pridobiVseNazive();
            idji = uporabnikDao.pridobiVseId();
            kraji = uporabnikDao.pridobiVseKraje();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Uporabnik pridobiPoId(long id) throws SQLException{
        Uporabnik u =uporabnikDao.pridobiPoId(id);
        return u;
    }

    public void pridobiKmetijo() throws  SQLException{
        izbranaKmetija = uporabnikDao.pridobiPoIdKmetijo(idIzbraneKmetije);
    }


    public long getIdIzbraneKmetije() { return idIzbraneKmetije;  }

    public void setIdIzbraneKmetije(long idIzbraneKmetije) { this.idIzbraneKmetije = idIzbraneKmetije;  }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Uporabnik getNovUporabnik() {
        return novUporabnik;
    }

    public void setNovUporabnik(Uporabnik noviUporabnik) {
        this.novUporabnik = noviUporabnik;
    }

    public List<String> getNaslovi() {
        return naslovi;
    }

    public void setNaslovi(List<String> naslovi) {
        this.naslovi = naslovi;
    }

    public UporabnikVmesnik getUporabnikDao() {
        return uporabnikDao;
    }

    public void setUporabnikDao(UporabnikVmesnik uporabnikDao) {
        this.uporabnikDao = uporabnikDao;
    }

    public List<String> getNazivi() {
        return nazivi;
    }

    public void setNazivi(List<String> nazivi) {
        this.nazivi = nazivi;
    }

    public List<String> getIdji() { return idji; }

    public void setIdji(List<String> idji) {  this.idji = idji;  }

    public Uporabnik getIzbranaKmetija() {    return izbranaKmetija;    }

    public void setIzbranaKmetija(Uporabnik izbranaKmetija) {    this.izbranaKmetija = izbranaKmetija;   }

    public long getIdVpisanegaUporabnika() {
        return idVpisanegaUporabnika;
    }

    public List<String> getKraji() {
        return kraji;
    }

    public void setKraji(List<String> kraji) {
        this.kraji = kraji;
    }

    public void setIdVpisanegaUporabnika(long idVpisanegaUporabnika) {
        this.idVpisanegaUporabnika = idVpisanegaUporabnika;
    }

    public void pripraviUporabnikaZaVpis(ComponentSystemEvent event) throws InterruptedException {
        /*FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();*/
    }

    public Uporabnik getVpisanUporabnik() {
        return vpisanUporabnik;
    }

    public void setVpisanUporabnik(Uporabnik vpisanUporabnik) {
        this.vpisanUporabnik = vpisanUporabnik;
    }

    public void spremeniKontakt() throws IOException{

        String id = Objects.toString(idVpisanegaUporabnika);
        try {
            System.out.println("UporabnikBean: spreminjam kontaktne podatke uporabniku " + id);
            uporabnikDao.spremeniKontakt(novUporabnik, id);

            novUporabnik = new UporabnikBuilder().createUporabnik();
        } catch (SQLException e) {
            System.out.println("UporabnikBean: nisem mogle spremeniti kontaktnih podatkov uporabniku " + id);
            e.printStackTrace();
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public void spremeniOpis() throws IOException{

        String id = Objects.toString(idVpisanegaUporabnika);
        try {
            System.out.println("UporabnikBean: spreminjam opis uporabniku " + id);
            uporabnikDao.spremeniOpis(novUporabnik, id);

            novUporabnik = new UporabnikBuilder().createUporabnik();
        } catch (SQLException e) {
            System.out.println("UporabnikBean: nisem mogle spremeniti opisa uporabniku " + id);
            e.printStackTrace();
        }

        /*ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());*/
    }

    public void shraniKmetijo() {

        String id = Objects.toString(idVpisanegaUporabnika);
        try {
            this.uporabnik = novUporabnik;
            System.out.println("UporabnikBean: shranjujem kmetijo "+id);
            uporabnikDao.shraniKmetijo(novUporabnik, idVpisanegaUporabnika);
        } catch (SQLException e) {
            System.out.println("UporabnikBean: nisem shraniti kmetije " + id);
            e.printStackTrace();
        }

    }

    public void nastaviVpisanegaUporabnika() throws SQLException {
        vpisanUporabnik = uporabnikDao.pridobiPoId(idVpisanegaUporabnika);
        System.out.println("TIP bean:"+vpisanUporabnik.getTip());
    }

    public String nastaviUporaboUporabnika() throws SQLException {
        return nastaviUporabo(1);
    }
    public String nastaviUporaboPonudnika() throws SQLException {
        return nastaviUporabo(2);
    }

    private String nastaviUporabo(int tip) throws SQLException {

        try {

            nastaviVpisanegaUporabnika();
            novUporabnik.setTip(tip);
            Uporabnik uporabnik = uporabnikDao.pridobiPoId(idVpisanegaUporabnika);
            uporabnik.setTip(tip);
            this.uporabnik = uporabnik;
            uporabnikDao.spremeniTip(uporabnik);
            if(tip == 2){
                naravnaDomacaStran = "vnos-izdelkov.xhtml";
                return "registracijaKmetije.xhtml?faces-redirect=true";
            }else{
                naravnaDomacaStran = "iskanje.xhtml";
                return "registracijaUporabnika.xhtml?faces-redirect=true";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "index.xhtml?faces-redirect=true";
        }

    }
    public void dodajNaSeznamPriljubljenih(long idKmetije, int idUporabnika){
        try {
            Uporabnik priljubljenaKmetija = uporabnikDao.pridobiPoId(idKmetije);
            Uporabnik narocnik = uporabnikDao.pridobiPoId(idUporabnika);
            priljubljeneKmetije = uporabnikDao.pridobiPriljubljene(narocnik);
            priljubljeneKmetije.add(priljubljenaKmetija);
            narocnik.setPriljubljeneKmetije(priljubljeneKmetije);
            uporabnikDao.dodajAliOdstraniPriljubljene(narocnik);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean jeMedPriljubljeni(long idKmetija, long idUporabnik){
        boolean jeNarocen = false;
        try {
            Uporabnik priljubljenaKmetija = uporabnikDao.pridobiPoId(idKmetija);
            Uporabnik narocnik = uporabnikDao.pridobiPoId(idUporabnik);
            priljubljeneKmetije = uporabnikDao.pridobiPriljubljene(narocnik);
            for (int i = 0; i<priljubljeneKmetije.size();i++){
                if (priljubljeneKmetije.get(i).getId()==priljubljenaKmetija.getId()) {
                    jeNarocen = true;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jeNarocen;
    }

    public void izbrisiIzSeznamaPriljubljenih(long idKmetija, long idUporabnik){
        try{
            Uporabnik priljubljenaKmetija = uporabnikDao.pridobiPoId(idKmetija);
            Uporabnik narocnik = uporabnikDao.pridobiPoId(idUporabnik);
            priljubljeneKmetije = uporabnikDao.pridobiPriljubljene(narocnik);
            for (int i = 0; i<priljubljeneKmetije.size();i++){
                if (priljubljeneKmetije.get(i).getId()==priljubljenaKmetija.getId()) {
                    priljubljeneKmetije.remove(i);
                }
            }
            narocnik.setPriljubljeneKmetije(priljubljeneKmetije);
            uporabnikDao.dodajAliOdstraniPriljubljene(narocnik);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
        public void pridobiPriljubljene (long idUporabnik){
            try {
                Uporabnik u = uporabnikDao.pridobiPoId(idUporabnik);
                priljubljeneKmetije = uporabnikDao.pridobiPriljubljene(u);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }





    public String getNaravnaDomacaStran() {
        return naravnaDomacaStran;
    }

    public void setNaravnaDomacaStran(String naravnaDomacaStran) {
        this.naravnaDomacaStran = naravnaDomacaStran;
    }

    public List<Uporabnik> getPriljubljeneKmetije() {
        return priljubljeneKmetije;
    }

    public void setPriljubljeneKmetije(List<Uporabnik> priljubljeneKmetije) {
        this.priljubljeneKmetije = priljubljeneKmetije;
    }
}


