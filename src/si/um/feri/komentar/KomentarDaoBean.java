package si.um.feri.komentar;

import si.um.feri.uporabnik.Uporabnik;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Klemen on 7. 06. 2017.
 */
@Stateless
@Local(KomentarDao.class)
public class KomentarDaoBean implements KomentarDao{

    @PersistenceContext
    EntityManager em;

    @Override
    public void shrani(Komentar k) throws Exception {
        em.merge(k);
    }

    @Override
    public List<Komentar> pridobiKomentarjeKmetije(Uporabnik kmetija) throws Exception {
        List<Komentar> komentarji = null;
        try {
            Query q = em.createQuery("select k from Komentar k where k.kmetija = :kmetija");
            q.setParameter("kmetija", kmetija);
            komentarji = (List<Komentar>) q.getResultList();
        } catch (Exception e){
            e.printStackTrace();
        }
        return komentarji;
    }

    @Override
    public Komentar pridobiKomentarUporabnikaInKmetije(Uporabnik kmetija, Uporabnik uporabnik) throws Exception {
        Komentar komentar=null;
        try {
            Query q = em.createQuery("select k from Komentar k where k.kmetija = :kmetija and k.uporabnik = :uporabnik");
            q.setParameter("kmetija", kmetija);
            q.setParameter("uporabnik", uporabnik);
            komentar = (Komentar) q.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        return komentar;
    }
}
