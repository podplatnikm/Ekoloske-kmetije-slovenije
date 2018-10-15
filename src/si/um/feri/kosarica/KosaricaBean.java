package si.um.feri.kosarica;

import si.um.feri.mail.MailKosarica;
import si.um.feri.mail.MailSender;
import si.um.feri.produkt.Produkt;
import si.um.feri.produkt.ProduktDao;
import si.um.feri.uporabnik.Uporabnik;
import si.um.feri.uporabnik.UporabnikDao;
import si.um.feri.uporabnik.UporabnikVmesnik;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Urh on 3.6.2017.
 */
@ManagedBean(name = "kosarica")
@SessionScoped
public class KosaricaBean implements Serializable {

    private List<Produkt> kosarica = new ArrayList<>();
    private HashMap<String, List<Produkt>> mapKosarica = new HashMap<String, List<Produkt>>();

    private int kolicina = 1;
    private HashMap<Produkt, Integer> mapKolicin = new HashMap<Produkt, Integer>();

    private List<Produkt> nakupNeuspesniProdukti;
    private boolean uspesenNakup;

    private double skupnaCena;

    @EJB
    ProduktDao produktDao;

    @EJB
    UporabnikVmesnik uporabnikDao;

    MailSender ms = MailSender.getInstance();

    private long idVpisanega;


    public HashMap<String, List<Produkt>> getMapKosarica() {

        if (mapKosarica.isEmpty()) {

            for (Produkt produkt : kosarica) {

                if (!mapKosarica.containsKey(produkt.getKmetija().getNaziv())) {
                    List<Produkt> list = new ArrayList<Produkt>();
                    list.add(produkt);

                    mapKosarica.put(produkt.getKmetija().getNaziv(), list);
                } else {
                    mapKosarica.get(produkt.getKmetija().getNaziv()).add(produkt);
                }

            }
        }
        return mapKosarica;
    }

    public List<Produkt> getKosarica() {
        return kosarica;
    }

    public void dodajProdukt(Produkt produkt) {
        if (!kosarica.contains(produkt)) {
            this.kosarica.add(produkt);
        }

        mapKosarica.clear();
        getMapKosarica();


        if (mapKolicin.containsKey(produkt)) {
            int vmesna = mapKolicin.get(produkt);
            int nova = vmesna + kolicina;
            mapKolicin.replace(produkt, vmesna, nova);
        } else {
            mapKolicin.put(produkt, kolicina);
        }
        kolicina = 1;
    }

    public void odstraniProdukt(Produkt produkt) {
        kosarica.remove(produkt);

        mapKosarica.get(produkt.getKmetija().getNaziv()).remove(produkt);
        if (mapKosarica.get(produkt.getKmetija().getNaziv()).size() == 0) {
            mapKosarica.remove(produkt.getKmetija().getNaziv());
        }
        mapKosarica.clear();
        getMapKosarica();

        mapKolicin.remove(produkt);
    }

    public double getCenaKmetije(String nazivKmetije) {
        double cenaKmetije = 0;
        if (mapKosarica.containsKey(nazivKmetije)) {
            List<Produkt> list = mapKosarica.get(nazivKmetije);

            for (Produkt p : list) {
                cenaKmetije += (p.getCena() * mapKolicin.get(p));
            }
        }

        cenaKmetije = zaokrozi(cenaKmetije);
        return cenaKmetije;
    }

    public double getSkupnaCena() {
        double cena = 0;

        for (Produkt p : kosarica) {
            cena = cena + (p.getCena() * mapKolicin.get(p));
        }
        skupnaCena = zaokrozi(cena);
        return skupnaCena;
    }

    public void kupi(long vpisan) throws Exception {
        idVpisanega = vpisan;
        boolean uspesno = true;
        List<Produkt> neuspesni = new ArrayList<>();
        int kolicina;
        int odsteteKolicina;

        for (Produkt p : kosarica) {
            if (p.getKolicina() < mapKolicin.get(p)) {
                neuspesni.add(p);
                uspesno = false;
            }
        }
        if (uspesno) {
            for (Produkt pr : kosarica) {
                kolicina = pr.getKolicina();
                odsteteKolicina = kolicina - mapKolicin.get(pr);

                if(odsteteKolicina > 0){
                    pr.setKolicina(odsteteKolicina);
                    produktDao.zmanjsajKolicino(pr, odsteteKolicina);
                }else if(odsteteKolicina == 0){
                    produktDao.odstrani(pr);
                }

            }
            posljiMail();
            this.kosarica.clear();
            this.mapKosarica.clear();
            this.mapKolicin.clear();
        }

        nakupNeuspesniProdukti = neuspesni;
        this.uspesenNakup = uspesno;
    }

    public HashMap<Produkt, Integer> getMapKolicin() {
        return mapKolicin;
    }

    public void setMapKolicin(HashMap<Produkt, Integer> mapKolicin) {
        this.mapKolicin = mapKolicin;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public List<Produkt> getNakupNeuspesniProdukti() {
        return nakupNeuspesniProdukti;
    }

    public boolean isUspesenNakup() {
        return uspesenNakup;
    }

    public static double zaokrozi(double d)
    {
        int c = 2;
        int temp = (int)(d * Math.pow(10 , c));
        return ((double)temp)/Math.pow(10 , c);
    }

    public void posljiMail(){
        String prejemnik;
        String mail;
        String naslov = "Obvestilo o naročilu.";
        StringBuilder sporocilo = new StringBuilder();

        List<String> zePoslani = new ArrayList<>();
        boolean niSe = true;
        try {

            sporocilo.append("<html><head></head>"
                    + "<body>"
                    + "<img src='http://i.imgur.com/I9mY8rT.png' alt='logo' style='height: 4em' />");

            for (Map.Entry<String, List<Produkt>> entry : mapKosarica.entrySet()) {

                if(niSe) {
                    prejemnik = entry.getKey();
                    Uporabnik u = uporabnikDao.pridobiPoNazivu(prejemnik);
                    mail = u.getEmail();
                    Uporabnik kupec = uporabnikDao.pridobiPoId(idVpisanega);

                    sporocilo.append("<h2> Oseba: "+kupec.getEmail()+" je narocila: </h2>");
                    sporocilo.append("<table style='border-style:dotted solid'>");
                    sporocilo.append("<tr><th>Produkt</th><th>Kolicina</th><th>Cena</th></tr>");

                    List<Produkt> produkti = entry.getValue();
                    for (Produkt p : produkti) {
                        sporocilo.append("<tr bgcolor='#57982E'>");
                        sporocilo.append("<td>"+p.getNaziv()+"</td>");
                        sporocilo.append("<td>"+mapKolicin.get(p)+"kg</td>");
                        sporocilo.append("<td>"+p.getCena()+"€</td>");
                        sporocilo.append("</tr>");

                    }
                    sporocilo.append("</table>");
                    sporocilo.append("<h3>");
                    sporocilo.append("<br/>Skupna cena: "+getCenaKmetije(entry.getKey())+"€");
                    sporocilo.append("<br/><br/>Kupec: "+kupec.getIme()+" "+kupec.getPriimek()+"");
                    sporocilo.append("<br/>Naslov: "+kupec.getNaslov()+"</b>");
                    sporocilo.append("<br/>Pošta: "+kupec.getPosta()+", "+kupec.getKraj()+"");
                    sporocilo.append("</h3>");
                    sporocilo.append("</body></html>");

                    ms.posljiEmail(mail,naslov,sporocilo.toString());
                    sporocilo = new StringBuilder();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
