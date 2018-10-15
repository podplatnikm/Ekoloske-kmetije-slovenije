package si.um.feri.produkt;

import si.um.feri.uporabnik.Uporabnik;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 24.5.2017.
 */

@Stateless
@Local(ProduktDao.class)
public class ProduktDaoBean implements ProduktDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void shrani(Produkt p) throws Exception {
        em.merge(p);

    }

    @Override
    public List<Produkt> pridobiVse(Uporabnik kmetija) throws Exception {
        List<Produkt> vsiProdukti = new ArrayList<>();
        try {
            Query query = em.createQuery("select p from Produkt p where p.kmetija = :kmetija");
            vsiProdukti = (List<Produkt>) query.setParameter("kmetija", kmetija).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vsiProdukti;
    }

    @Override
    public Produkt pridobiPoId(long id) throws Exception {
        Query q = em.createQuery("select p from Produkt p where p.id = :e");
        q.setParameter("e", id);

        return (Produkt) q.getSingleResult();
    }

    @Override
    public Produkt pridobiPoNazivu(String naziv) throws Exception {
        Query q = em.createQuery("select p from Produkt p where p.naziv = :e");
        q.setParameter("e", naziv);

        return (Produkt) q.getSingleResult();
    }

    @Override
    public List<Produkt> pridobiPoKategoriji(String kategorija) throws Exception {
        Query q = em.createQuery("select p from Produkt p where p.kategorija = :e");
        q.setParameter("e", kategorija);

        List<Produkt> vsiProduktiKategorija = q.getResultList();

        return vsiProduktiKategorija;
    }

    @Override
    public void zmanjsajKolicino(Produkt pDobljen, int kolicina) throws Exception {
        long id = pDobljen.getId();

        Query q = em.createQuery("update Produkt p set p.kolicina = :kolicina where p.id = :e");
        q.setParameter("kolicina", kolicina);
        q.setParameter("e", id);
        q.executeUpdate();
    }

    @Override
    public void odstrani(Produkt p) throws Exception {
        long id = p.getId();

        Query q = em.createQuery("Delete from Produkt p where p.id = :e");
        q.setParameter("e", id);
        q.executeUpdate();

    }




}
