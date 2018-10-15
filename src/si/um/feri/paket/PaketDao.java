package si.um.feri.paket;

import si.um.feri.uporabnik.Uporabnik;

import java.util.List;

/**
 * Created by David on 4.6.2017.
 */
public interface PaketDao {

    void shrani (Paket p) throws Exception;

    void ustvariIzbrisiNarocnino (Paket p,Uporabnik u) throws Exception;

    void izbrisiPaket (Paket p) throws Exception;

    Paket pridobiPaket (Uporabnik kmetija) throws Exception;

    List<Paket> pridobiPakete (Uporabnik narocnik) throws Exception;

    long pridobiKmetijo (long idPaket) throws Exception;

    List<Uporabnik> pridobiNarocnike (Uporabnik kmetija) throws Exception;


}
