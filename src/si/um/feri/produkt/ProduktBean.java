package si.um.feri.produkt;

import si.um.feri.uporabnik.Uporabnik;
import si.um.feri.uporabnik.UporabnikVmesnik;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by David on 24.5.2017.
 */
@ManagedBean("produktBean")
@SessionScoped
public class ProduktBean implements Serializable {


    public Produkt noviProdukt = new ProduktBuilder().createProdukt();
    public Produkt izbraniProdukt = new ProduktBuilder().createProdukt();
    public Uporabnik kmetija;
    public List<Produkt> vsiProdukti = new ArrayList<>();

    private int kolicinaKupljenega;

    @EJB
    ZnackaVmesnik zDao;

    @EJB
    ProduktDao produktDao;

    @EJB
    UporabnikVmesnik uporabnikDao;

    public void shraniProdukt(long idKmetija) {
        try {
            kmetija = uporabnikDao.pridobiPoId(idKmetija);

            List<Znacka> listZnack = pridobiZnackeIzNiza(noviProdukt.getNizOznak());

            noviProdukt.setDatumKreacije(Calendar.getInstance());
            noviProdukt.setKmetija(kmetija);
            noviProdukt.setZnacke(listZnack);

            produktDao.shrani(noviProdukt);

            noviProdukt = new ProduktBuilder().createProdukt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Znacka> pridobiZnackeIzNiza(String nizOznak) throws SQLException {
        List<Znacka> listaZnack = new ArrayList<>();
        for(String s : Arrays.asList(nizOznak.split(","))){
            Znacka tempZnacka = new ZnackaBuilder().setOznaka(s).createZnacka();
            zDao.shrani(tempZnacka);
            listaZnack.add(tempZnacka);
        }
        return listaZnack;

    }


    public List<Produkt> pridobitevVseh(long idKmetija) {
        try {
            System.out.println(idKmetija);
            kmetija = uporabnikDao.pridobiPoId(idKmetija);
            vsiProdukti = produktDao.pridobiVse(kmetija);
            return vsiProdukti;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vsiProdukti;
    }

    public Produkt getNoviProdukt() {
        return noviProdukt;
    }

    public void setNoviProdukt(Produkt noviProdukt) {
        this.noviProdukt = noviProdukt;
    }

    public String odstraniProdukt(long idProdukt) throws Exception {
        produktDao.odstrani(produktDao.pridobiPoId(idProdukt));
        return "pregledIzdelkov.xhtml?faces-redirect=true";
    }

}

