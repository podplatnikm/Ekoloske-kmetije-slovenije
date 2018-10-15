package si.um.feri.paket;

import si.um.feri.mail.MailPaket;
import si.um.feri.uporabnik.Uporabnik;
import si.um.feri.uporabnik.UporabnikVmesnik;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 4.6.2017.
 */

@ManagedBean("paketBean")
@RequestScoped
public class PaketBean implements Serializable{

    public Paket novPaket = new Paket();
    public Paket obstojeciPaket = new Paket();
    public Uporabnik kmetija;
    public Uporabnik narocnik;
    List<Uporabnik> narocniki = new ArrayList<>();
    List<Paket> paketi = new ArrayList<>();

    @EJB
    PaketDao paketDao;
    @EJB
    UporabnikVmesnik uporabnikDao;


    public void shraniPaket(long idKmetija) {
        try {
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            novPaket.setKmetija(kmetija);
            paketDao.shrani(novPaket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void izbrisPaketa(long idKmetija) {
        try {
            Paket zaIzbris = paketDao.pridobiPaket(kmetija);
            paketDao.izbrisiPaket(zaIzbris);
        }

         catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dodajNarocnika(long idKmetija, long idUporabnik) {
        try {
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            narocnik = uporabnikDao.pridobiPoId(idUporabnik);
            narocniki = paketDao.pridobiNarocnike(kmetija);
            narocniki.add(narocnik);
            obstojeciPaket.setUporabnik(narocniki);
            paketi.add(obstojeciPaket);
            narocnik.setPaketi(paketi);
            paketDao.ustvariIzbrisiNarocnino(obstojeciPaket,narocnik);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void izbrisiNarocnika(long idKmetija, long idUporabnik){
        try{
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            narocnik = uporabnikDao.pridobiPoId(idUporabnik);
            narocniki = paketDao.pridobiNarocnike(kmetija);
            paketi = paketDao.pridobiPakete(narocnik);
            for (int i = 0; i<narocniki.size();i++){
                if (narocniki.get(i).getId()==narocnik.getId()) {
                    narocniki.remove(i);
                }
            }
            for (int i = 0; i<paketi.size();i++){
                if (paketi.get(i).getId()==obstojeciPaket.getId()) {
                    paketi.remove(i);
                }
            }

            obstojeciPaket.setUporabnik(narocniki);
            narocnik.setPaketi(paketi);
            paketDao.ustvariIzbrisiNarocnino(obstojeciPaket,narocnik);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void razposljiPakete(long idKmetija) {

        try {
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            List<Uporabnik> narocniki;
            narocniki = paketDao.pridobiNarocnike(kmetija);
            Paket p;
            p = paketDao.pridobiPaket(kmetija);
            MailPaket mailPaket = new MailPaket();
            mailPaket.razposlji(narocniki,kmetija,p);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean jeNarocen(long idKmetija, long idUporabnik){
        boolean jeNarocen = false;
        try {
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            narocnik = uporabnikDao.pridobiPoId(idUporabnik);
            narocniki = paketDao.pridobiNarocnike(kmetija);
            for (int i = 0; i<narocniki.size();i++){
                if (narocniki.get(i).getId()==narocnik.getId()) {
                    jeNarocen = true;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jeNarocen;
    }

    public Paket pridobiPaket(long idKmetija) {

        try {
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            obstojeciPaket = paketDao.pridobiPaket(kmetija);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obstojeciPaket;
    }

    public long pridobiKmetijo(long idPaketa) {
        long idKmetije=0;
        try {
            idKmetije = paketDao.pridobiKmetijo(idPaketa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idKmetije;
    }

    public void pridobiPakete(long idUporabnik) {
        try {
            narocnik = uporabnikDao.pridobiPoId(idUporabnik);
            paketi = paketDao.pridobiPakete(narocnik);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Paket getNovPaket() {
        return novPaket;
    }

    public void setNovPaket(Paket novPaket) {
        this.novPaket = novPaket;
    }

    public Paket getObstojeciPaket() {
        return obstojeciPaket;
    }

    public void setObstojeciPaket(Paket obstojeciPaket) {
        this.obstojeciPaket = obstojeciPaket;
    }

    public Uporabnik getKmetija() {
        return kmetija;
    }

    public void setKmetija(Uporabnik kmetija) {
        this.kmetija = kmetija;
    }

    public Uporabnik getNarocnik() {
        return narocnik;
    }

    public void setNarocnik(Uporabnik narocnik) {
        this.narocnik = narocnik;
    }

    public List<Uporabnik> getNarocniki() {
        return narocniki;
    }

    public void setNarocniki(List<Uporabnik> narocniki) {
        this.narocniki = narocniki;
    }

    public List<Paket> getPaketi() {
        return paketi;
    }
    public void setPaketi(List<Paket> paketi) {
        this.paketi = paketi;
    }
}
