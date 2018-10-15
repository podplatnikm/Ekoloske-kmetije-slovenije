package si.um.feri.komentar;

import si.um.feri.uporabnik.Uporabnik;
import si.um.feri.uporabnik.UporabnikVmesnik;

import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klemen on 7. 06. 2017.
 */

@ManagedBean(name="komentarBean")
@SessionScoped
public class KomentarBean {
    @EJB
    KomentarDao komentarDao;
    @EJB
    UporabnikVmesnik uporabnikDao;
    public Komentar komentar = new Komentar();
    public Uporabnik uporabnik;
    public Uporabnik kmetija;
    public String besedilo;
    public List<Komentar> komentarji = new ArrayList<>();
    public boolean komentarZeOddan=false;
    public int ocena;
    public double povprecnaOcena;


    public void pridobiUporabnika(long id){
        try {
            uporabnik = uporabnikDao.pridobiPoId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void shraniKomentar(long idKmetija, long idUporabnik){
        try {
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            uporabnik = uporabnikDao.pridobiPoId(idUporabnik);
            komentar.setKomentar(besedilo);
            komentar.setKmetija(kmetija);
            komentar.setUporabnik(uporabnik);
            komentar.setOcena(ocena);
            if (komentarZeOddan == false) {
                komentarDao.shrani(komentar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pridobiKomentarje(long idKmetija){
        komentarZeOddan=false;
        besedilo="";
        double steviloOcen=0;
        double sestevekOcen=0;
        try {
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            komentarji = komentarDao.pridobiKomentarjeKmetije(kmetija);
            steviloOcen = komentarji.size();
            //Preverjanje ali je uporabnik ze oddal komentar
            for(Komentar komentar:komentarji){
                sestevekOcen += komentar.getOcena();
                if(komentar.getUporabnik().getId()==uporabnik.getId()){
                    komentarZeOddan=true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        povprecnaOcena = sestevekOcen/steviloOcen;
    }

    public Komentar getKomentar() {
        return komentar;
    }

    public void setKomentar(Komentar komentar) {
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

    public String getBesedilo() {
        return besedilo;
    }

    public void setBesedilo(String besedilo) {
        this.besedilo = besedilo;
    }

    public List<Komentar> getKomentarji() {
        return komentarji;
    }

    public void setKomentarji(List<Komentar> komentarji) {
        this.komentarji = komentarji;
    }

    public boolean isKomentarZeOddan() {  return komentarZeOddan;  }

    public void setKomentarZeOddan(boolean komentarZeOddan) {   this.komentarZeOddan = komentarZeOddan;  }

    public int getOcena() {   return ocena;  }

    public void setOcena(int ocena) {   this.ocena = ocena;  }

    public double getPovprecnaOcena() {   return povprecnaOcena;  }

    public void setPovprecnaOcena(double povprecnaOcena) {    this.povprecnaOcena = povprecnaOcena;  }
}
