package si.um.feri.mail;

import si.um.feri.produkt.Produkt;
import si.um.feri.uporabnik.Uporabnik;
import si.um.feri.uporabnik.UporabnikVmesnik;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 5.6.2017.
 */

public class MailKosarica {

    @EJB
    UporabnikVmesnik uporabnikDao;

    public void poslji (String prejemnik, String naslov, String vsebina){


    }
}
