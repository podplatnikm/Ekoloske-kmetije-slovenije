package si.um.feri.varnost;

import com.google.gson.Gson;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import si.um.feri.uporabnik.Uporabnik;
import si.um.feri.uporabnik.UporabnikBean;
import si.um.feri.uporabnik.UporabnikBuilder;
import si.um.feri.uporabnik.UporabnikVmesnik;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by Miha on 30/05/2017.
 */
@WebServlet(name = "FacebookOAuth2callback", urlPatterns = "/FacebookOAuth2callback")
public class FacebookOAuth2callback extends HttpServlet {

    @EJB
    UporabnikVmesnik uporabnikVmesnik;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String napaka = request.getParameter("error");
            if(( napaka != null ) && ("access_denied".equals(napaka.trim()))){
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect(request.getContextPath());
            }

            HttpSession session = request.getSession(false);
            OAuthService service = (OAuthService) session.getAttribute("oauth2Service");

            String code = request.getParameter("code");

            Token token = service.getAccessToken(null, new Verifier(code));
            session.setAttribute("token", token);

            OAuthRequest oReq = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me?fields=name,email,first_name,last_name");
            service.signRequest(token, oReq);

            Response oRes = oReq.send();

            Gson g = new Gson();
            FacebookUporabnik facebookUporabnik = g.fromJson(oRes.getBody(), FacebookUporabnik.class);
            Uporabnik noviUporabnik = uporabnikVmesnik.pridobiPoApiId(facebookUporabnik.getId());

            if(noviUporabnik == null){
                noviUporabnik = new UporabnikBuilder()
                        .setEmail(facebookUporabnik.getEmail())
                        .setIme(facebookUporabnik.getFirst_name())
                        .setPriimek(facebookUporabnik.getLast_name())
                        .setIdApi(facebookUporabnik.getId())
                        .setTip(0)
                        .setDatumKreacije(Calendar.getInstance())
                        .setPosta(0)
                        .createUporabnik();


                /*noviUporabnik = new UporabnikBuilder().createUporabnik();
                noviUporabnik.setEmail(facebookUporabnik.getEmail());

                noviUporabnik.setNaziv(null);
                noviUporabnik.setOpis(null);
                noviUporabnik.setTelefonskaStevilka(null);
                noviUporabnik.setNaslov(null);
                noviUporabnik.setPosta(0);
                noviUporabnik.setKraj(null);
                noviUporabnik.setIme(facebookUporabnik.getFirst_name());
                noviUporabnik.setPriimek(facebookUporabnik.getLast_name());

                noviUporabnik.setIdApi(facebookUporabnik.getId());
                noviUporabnik.setTip(0);*/

                uporabnikVmesnik.shrani(noviUporabnik);
                System.out.println("VPIS: Novi Uporabnik");
            }else{
                System.out.println("VPIS: Najden uporabnik");
            }


            UporabnikBean bean = (UporabnikBean) request.getSession().getAttribute("uporabnikBean");
            bean.setIdVpisanegaUporabnika(noviUporabnik.getId());

            switch(noviUporabnik.getTip()){
                case 0:

                    System.out.println("TIP UPORABNIKA: "+noviUporabnik.getTip());
                    session.setAttribute("uporabnik", noviUporabnik);
                    request.getRequestDispatcher("izbiraUporabe.xhtml").forward(request, response);
                    return;

                case 1:

                    System.out.println("TIP UPORABNIKA: "+noviUporabnik.getTip());
                    bean.setNaravnaDomacaStran("iskanje.xhtml");
                    session.setAttribute("uporabnik", noviUporabnik);
                    request.getRequestDispatcher("iskanje.xhtml").forward(request, response);
                    return;

                case 2:

                    System.out.println("TIP UPORABNIKA: "+noviUporabnik.getTip());
                    bean.setNaravnaDomacaStran("vnos-izdelkov.xhtml");
                    session.setAttribute("uporabnik", noviUporabnik);
                    request.getRequestDispatcher("vnos-izdelkov.xhtml").forward(request, response);
                    return;
            }

            /*session.setAttribute("uporabnik", noviUporabnik);

            System.out.println("TIP UPORABNIKA: "+noviUporabnik.getTip());
            if(noviUporabnik.getTip() == 0){
                request.getRequestDispatcher("izbiraUporabe.xhtml").forward(request, response);
                return;
            }else{
                request.getRequestDispatcher("vpis.xhtml").forward(request, response);
                return;
            }*/


        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession(true);
            request.getRequestDispatcher("index.xhtml").forward(request, response);
        }


    }
}
