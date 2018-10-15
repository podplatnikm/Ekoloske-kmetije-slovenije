package si.um.feri.poslusalci;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import si.um.feri.produkt.*;
import si.um.feri.uporabnik.Uporabnik;
import si.um.feri.uporabnik.UporabnikBuilder;
import si.um.feri.uporabnik.UporabnikVmesnik;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miha on 02/06/2017.
 */
@ManagedBean("iskanje")
@SessionScoped
public class Iskanje implements Serializable{

    private String iskalniNiz;
    private List rezultatIskanja;
    private int statusIskanja = 0;

    @EJB
    ProduktDao pDao;

    @EJB
    ZnackaVmesnik zDao;

    @EJB
    UporabnikVmesnik uDao;


    @PersistenceContext
    EntityManager entityManager;

    public String isci() throws Exception {


        try {

            System.out.println("V iskanju: "+iskalniNiz);

            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
            QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Produkt.class).get();

            org.apache.lucene.search.Query luceneQuery = qb
                    .keyword()
                    .onFields("naziv", "opis", "kategorija", "znacke.oznaka", "kmetija.email", "kmetija.naziv", "kmetija.kraj", "kmetija.ime", "kmetija.priimek")
                    .matching(iskalniNiz)
                    .createQuery();

            javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Produkt.class);
            rezultatIskanja = jpaQuery.getResultList();

            if(rezultatIskanja.size() == 0){
                System.out.println("Nenajdem rezultatov");
                statusIskanja = 1;
            }else{
                statusIskanja = 2;
            }

            return "iskanje.xhtml?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            return "iskanje.xhtml?faces-redirect=true";
        }
    }

    private void testiskanja() {
        Uporabnik prviUporabnik = new UporabnikBuilder()
                .setEmail("test@gmail.com")
                .setNaziv("testNaziv")
                .setIme("testIme")
                .setPriimek("testPriimek")
                .createUporabnik();

        Znacka prvaZnacka = new ZnackaBuilder()
                .setOznaka("OznakaEna")
                .createZnacka();
        Znacka drugaZnacka = new ZnackaBuilder()
                .setOznaka("OznakaDva")
                .createZnacka();

        List<Znacka> vseZnacke = new ArrayList<>();
        vseZnacke.add(prvaZnacka);
        vseZnacke.add(drugaZnacka);

        Produkt prviProdukt = new ProduktBuilder()
                .setNaziv("Naziv")
                .setCena(10.1d)
                .setKategorija("Kategorija")
                .setOpis("opis")
                .setKmetija(prviUporabnik)
                .setZnacke(vseZnacke)
                .createProdukt();

        List<Produkt> p = new ArrayList<>();
        p.add(prviProdukt);
        this.rezultatIskanja = p;

    }

    public String getIskalniNiz() {
        return iskalniNiz;
    }

    public void setIskalniNiz(String iskalniNiz) {
        this.iskalniNiz = iskalniNiz;
    }

    public List getRezultatIskanja() {
        return rezultatIskanja;
    }

    public void setRezultatIskanja(List rezultatIskanja) {
        this.rezultatIskanja = rezultatIskanja;
    }

    public int getStatusIskanja() {
        return statusIskanja;
    }

    public void setStatusIskanja(int statusIskanja) {
        this.statusIskanja = statusIskanja;
    }

    public String isciKategorijo(String kategorija) throws Exception {
        this.iskalniNiz = kategorija;
        return isci();
    }
}
