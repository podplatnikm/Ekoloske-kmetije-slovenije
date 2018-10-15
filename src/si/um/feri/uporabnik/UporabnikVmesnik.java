package si.um.feri.uporabnik;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Miha on 22/05/2017.
 */

@Local
public interface UporabnikVmesnik {

    boolean shrani(Uporabnik noviUporabnik) throws SQLException;

    boolean shraniKmetijo(Uporabnik uporabnik, long id) throws SQLException;

    Uporabnik pridobiPoApiId(String apiID) throws  SQLException;

    Uporabnik pridobiPoId(long id) throws SQLException;

    Uporabnik pridobiPoNazivu(String naziv) throws SQLException;

    Uporabnik pridobiPoIdKmetijo(long id) throws SQLException;

    Uporabnik pridobiPoEmailu(String email) throws SQLException;

    List<String> pridobiVseNaslove() throws SQLException;

    List<String> pridobiVseNazive() throws SQLException;

    List<String> pridobiVseId() throws  SQLException;

    List<String> pridobiVseKraje() throws  SQLException;

    List<Uporabnik> pridobiVse() throws SQLException;

    void spremeniKontakt(Uporabnik uporabnik, String apiID) throws  SQLException;

    void spremeniOpis(Uporabnik uporabnik, String apiID) throws  SQLException;

    boolean spremeniTip(Uporabnik uporabnik) throws  SQLException;

    void dodajAliOdstraniPriljubljene(Uporabnik u) throws  SQLException;

    List<Uporabnik> pridobiPriljubljene(Uporabnik u) throws SQLException;
}
