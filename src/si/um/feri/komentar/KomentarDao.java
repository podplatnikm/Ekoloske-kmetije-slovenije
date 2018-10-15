package si.um.feri.komentar;

import si.um.feri.uporabnik.Uporabnik;

import java.util.List;

/**
 * Created by Klemen on 7. 06. 2017.
 */
public interface KomentarDao {

    void shrani (Komentar k) throws Exception;

    List<Komentar> pridobiKomentarjeKmetije (Uporabnik kmetija) throws Exception;

    Komentar pridobiKomentarUporabnikaInKmetije (Uporabnik kmetija, Uporabnik uporabnik) throws Exception;
}
