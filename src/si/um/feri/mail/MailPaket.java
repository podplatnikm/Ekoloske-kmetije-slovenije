package si.um.feri.mail;


import si.um.feri.paket.Paket;
import si.um.feri.uporabnik.Uporabnik;
import java.util.List;

/**
 * Created by David on 5.6.2017.
 */

public class MailPaket {

    public void razposlji (List<Uporabnik> narocniki, Uporabnik kmetija, Paket p){

        String nazivKmetije = kmetija.getNaziv();
        MailSender mailSender = MailSender.getInstance();
        StringBuilder nasloviNarocnikov = new StringBuilder();
        nasloviNarocnikov.append("<html><head></head>"
                + "<body>"
                + "<img src='http://i.imgur.com/I9mY8rT.png' alt='logo' style='height: 4em' />");
        nasloviNarocnikov.append("<h2> Potrebno je dostaviti paket na naslove </h2>");
        nasloviNarocnikov.append("<table style='border-style:dotted solid'>");
        StringBuilder drugo = new StringBuilder();
        drugo.append("<html><head></head>"
                + "<body>"
                + "<img src='http://i.imgur.com/I9mY8rT.png' alt='logo' style='height: 4em' />");
        drugo.append("<h2>Pozdravljeni, </h2>");
        if(narocniki.size()>0) {
            try {
                for (int i = 0; i < narocniki.size(); i++) {
                    String mailNarocnika = narocniki.get(i).getEmail();

                    drugo.append("<h2>Vaš tedenski paket je bil s strani kmetije "+ nazivKmetije+" odposlan.</h2>");
                    drugo.append("<h2>Cena paketa znaša "+p.getCena()+"€. Paket vam bo v najhitrejšem možnem času dostavljen na dom. Paket plačate ob prevzemu.</h2>");
                    mailSender.posljiEmail(mailNarocnika, "Odposlan paket",drugo.toString());

                    nasloviNarocnikov.append("<tr bgcolor='#57982E'>");
                    nasloviNarocnikov.append("<td>");
                    nasloviNarocnikov.append(narocniki.get(i).getNaslov() + ", " + narocniki.get(i).getPosta() + " " + narocniki.get(i).getKraj() + "</br>");
                    nasloviNarocnikov.append("</td>");
                    nasloviNarocnikov.append("</tr>");

                }
                nasloviNarocnikov.append("</table></body></html>");
                mailSender.posljiEmail(kmetija.getEmail(), "Dostava paketov", nasloviNarocnikov.toString());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            drugo.append("<h2> Na vaš paket ni naročena nobena oseba. </h2>");
            mailSender.posljiEmail(kmetija.getEmail(),"Paket",drugo.toString());
            }


    }
}
