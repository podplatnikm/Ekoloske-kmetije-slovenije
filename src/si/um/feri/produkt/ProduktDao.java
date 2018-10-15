package si.um.feri.produkt;

import si.um.feri.uporabnik.Uporabnik;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by David on 24.5.2017.
 */
@Local
public interface ProduktDao {

    void shrani (Produkt p) throws Exception;

    List<Produkt> pridobiVse (Uporabnik kmetija) throws Exception;

    Produkt pridobiPoId (long id) throws Exception;

    Produkt pridobiPoNazivu (String naziv) throws Exception;

    void zmanjsajKolicino(Produkt p, int kolicina) throws  Exception;

    List<Produkt> pridobiPoKategoriji (String kategorija) throws Exception;

    void odstrani(Produkt p) throws Exception;

}
