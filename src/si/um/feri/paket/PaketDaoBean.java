package si.um.feri.paket;

import si.um.feri.uporabnik.Uporabnik;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by David on 4.6.2017.
 */
@Stateless
public class PaketDaoBean implements PaketDao {
    @PersistenceContext
    EntityManager em;


    @Override
    public void shrani(Paket p) throws Exception {
            em.merge(p);
    }

    @Override
    public void ustvariIzbrisiNarocnino(Paket p,Uporabnik u) throws Exception {
        try {
            em.merge(p);
            em.merge(u);
        }
        catch (Exception e){
            em.merge(p);
            em.merge(u);
        }
    }
    @Override
    public void izbrisiPaket(Paket p) throws Exception {
        Query query = em.createQuery("delete from Paket p where p.id= :paket");
        query.setParameter("paket",p.getId()).executeUpdate();
    }


    @Override
    public Paket pridobiPaket(Uporabnik kmetija) throws Exception {
        Paket paket = null;
        try {
            Query query = em.createQuery("select p from Paket p where p.kmetija = :kmetija");
            paket = (Paket) query.setParameter("kmetija", kmetija).getSingleResult();
        } catch (Exception e) {
            System.out.println("PaketDaoBean.java: EXCEPTION - Nemorem pridobiti paketa");
        }
        return paket;
    }

    @Override
    public List<Paket> pridobiPakete(Uporabnik narocnik) throws Exception {

        List<Paket> paketi = new ArrayList<>();
        try {
            Query query = em.createQuery("select paketi from Uporabnik p where p.id = :narocnik");
            paketi = (List<Paket>) query.setParameter("narocnik", narocnik.getId()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paketi;
    }

    @Override
    public long pridobiKmetijo(long idPaket) throws Exception {
        Uporabnik kmetija = null;
        try {
            Query query = em.createQuery("select kmetija from Paket p where p.id = :idPaket");
            kmetija = (Uporabnik) query.setParameter("idPaket", idPaket).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kmetija.getId();
    }

    @Override
    public List<Uporabnik> pridobiNarocnike(Uporabnik kmetija) throws Exception {
        List<Uporabnik> vsiNarocniki = new ArrayList<>();
        try {
            Query query = em.createQuery("select p.uporabnik from Paket p where p.kmetija = :kmetija");
            vsiNarocniki = (List<Uporabnik>) query.setParameter("kmetija", kmetija).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vsiNarocniki;
    }
}
