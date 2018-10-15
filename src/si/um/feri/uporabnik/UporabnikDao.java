package si.um.feri.uporabnik;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Miha on 22/05/2017.
 */

@Stateless
public class UporabnikDao implements UporabnikVmesnik{


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean shrani(Uporabnik noviUporabnik) throws SQLException {
        try {
            entityManager.persist(noviUporabnik);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean shraniKmetijo(Uporabnik u, long id) throws SQLException {
        try{
            try {
                System.out.println("UporabnikDao: shranjujem kmetijo "+id);
                int tip;
                if(u.getNaziv().isEmpty()){
                    tip = 1;
                }else{
                    tip = 2;
                }

                Query query = entityManager.createQuery("Update Uporabnik Set naziv = :naziv, " +
                        "opis = :opis, telefonskaStevilka = :telefon, naslov = :naslov," +
                        " posta = :posta, kraj = :kraj, tip = :tip where id = :id");
                query.setParameter("naziv",u.getNaziv());
                query.setParameter("opis",u.getOpis());
                query.setParameter("telefon",u.getTelefonskaStevilka());
                query.setParameter("naslov",u.getNaslov());
                query.setParameter("posta",u.getPosta());
                query.setParameter("kraj",u.getKraj());
                query.setParameter("tip", tip);
                query.setParameter("id", id);
                query.executeUpdate();


            } catch (Exception e) {
                System.out.println("QUERY: Ne najdem uporabnika po API ID-ju. Ali pa nisem mogel commitati sprememb!");
                e.printStackTrace();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Uporabnik pridobiPoApiId(String apiID) throws SQLException {
        try {
            Query query = entityManager.createQuery("select u from Uporabnik u where u.idApi = :idApiParameter");
            Uporabnik u = (Uporabnik) query.setParameter("idApiParameter", apiID).getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println("QUERY: Ne najdem uporabnika po API ID-ju!");
            return null;
        }
    }

    @Override
    public Uporabnik pridobiPoId(long id) throws SQLException {
        try {
            Query query = entityManager.createQuery("select u from Uporabnik u where u.id = :id");
            Uporabnik u = (Uporabnik) query.setParameter("id", id).getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println("QUERY: Ne najdem uporabnika po ID-ju!");
            return null;
        }
    }

    @Override
    public Uporabnik pridobiPoIdKmetijo(long id) throws SQLException {
        try {
            Query query = entityManager.createQuery("select u from Uporabnik u where u.tip = 2 and u.id = :id");
            query.setParameter("id", id);
            Uporabnik u = (Uporabnik) query.getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println("QUERY: Ne najdem uporabnika po API ID-ju!");
            return null;
        }
    }

    @Override
    public Uporabnik pridobiPoEmailu(String email) throws SQLException {
        try {
            Query query = entityManager.createQuery("select u from Uporabnik u where u.email = :email");
            Uporabnik u = (Uporabnik) query.setParameter("email", email).getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println("QUERY: Ne najdem uporabnika po API ID-ju!");
            return null;
        }
    }

    @Override
    public List<String> pridobiVseNaslove() throws SQLException{
        Query query=entityManager.createQuery("select u.naslov from Uporabnik u where u.tip=2");
        return (List<String>) query.getResultList();
    }

    @Override
    public List<String> pridobiVseKraje() throws SQLException{
        Query query=entityManager.createQuery("select u.kraj from Uporabnik u where u.tip=2");
        return (List<String>) query.getResultList();
    }

    @Override
    public List<String> pridobiVseNazive() throws SQLException{
        Query query=entityManager.createQuery("select u.naziv from Uporabnik u where u.tip=2");
        return (List<String>) query.getResultList();
    }

    @Override
    public List<String> pridobiVseId() throws SQLException{
        Query query=entityManager.createQuery("select u.id from Uporabnik u where u.tip=2");
        return (List<String>) query.getResultList();
    }

    @Override
    public List<Uporabnik> pridobiVse() throws SQLException {

        try {
            Query query = entityManager.createQuery("select u from Uporabnik u");
            return (List<Uporabnik>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void spremeniKontakt(Uporabnik u, String idKmetije) throws SQLException {
        try {
            System.out.println("UporabnikDao: spreminjam kontaktne podatke kmetije "+idKmetije);
            Long id = Long.parseLong(idKmetije);
            Query query = entityManager.createQuery("Update Uporabnik Set kraj = :kraj, " +
                    "naslov = :naslov, posta = :posta, telefonskaStevilka = :telefon where id = :id");
            query.setParameter("kraj",u.getKraj());
            query.setParameter("naslov",u.getNaslov());
            query.setParameter("posta",u.getPosta());
            query.setParameter("telefon",u.getTelefonskaStevilka());
            query.setParameter("id", id);
            query.executeUpdate();


        } catch (Exception e) {
            System.out.println("QUERY: Ne najdem uporabnika po API ID-ju. Ali pa nisem mogel commitati sprememb!");
            e.printStackTrace();
        }
    }

    @Override
    public void spremeniOpis(Uporabnik u, String idKmetije) throws SQLException {
        try {
            System.out.println("UporabnikDao: spreminjam opis kmetije "+idKmetije);
            Long id = Long.parseLong(idKmetije);
            Query query = entityManager.createQuery("Update Uporabnik Set opis = :opis where id = :id");
            query.setParameter("opis",u.getOpis());
            query.setParameter("id", id);
            query.executeUpdate();


        } catch (Exception e) {
            System.out.println("QUERY: Ne najdem uporabnika po API ID-ju. Ali pa nisem mogel commitati sprememb!");
            e.printStackTrace();
        }
    }

    @Override
    public boolean spremeniTip(Uporabnik uporabnik) throws SQLException {

        try {
            entityManager.merge(uporabnik);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void dodajAliOdstraniPriljubljene(Uporabnik u) throws SQLException {
        System.out.println("Dodajam med priljubljene"+u.getIme());
        try {
            entityManager.merge(u);
        }
        catch (Exception e){
            entityManager.merge(u);
        }
    }

    @Override
    public List<Uporabnik> pridobiPriljubljene(Uporabnik u) throws SQLException {
        List<Uporabnik> priljubljeneKmetije = null;
        try {
            Query query = entityManager.createQuery("select priljubljeneKmetije from Uporabnik u where u.id = :id");
            priljubljeneKmetije = (List<Uporabnik>) query.setParameter("id", u.getId()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return priljubljeneKmetije;
    }


    @Override
    public Uporabnik pridobiPoNazivu(String naziv) throws SQLException {
        try {
            Query query = entityManager.createQuery("select u from Uporabnik u where u.naziv = :naziv");
            Uporabnik u = (Uporabnik) query.setParameter("naziv", naziv).getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println("QUERY: Ne najdem uporabnika po API ID-ju!");
            return null;
        }
    }
    
}
