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
 * Created by Miha on 21/05/2017.
 */

@WebServlet("/GoogleOAuth2callback")
public class GoogleOAuth2callback extends HttpServlet {


    @EJB
    UporabnikVmesnik uporabnikVmesnik;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String napaka = request.getParameter("error");
            if(( napaka != null ) && ("access_denied".equals(napaka.trim()))){
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect(request.getContextPath());
                return;
            }

            HttpSession session = request.getSession(false);
            OAuthService service = (OAuthService) session.getAttribute("oauth2Service");



            String code = request.getParameter("code");
            Token token = service.getAccessToken(null, new Verifier(code));

            session.setAttribute("token", token);

            OAuthRequest oReq = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
            service.signRequest(token, oReq);
            Response oRes = oReq.send();

            Gson g = new Gson();
            GoogleUporabnik googleUporabnik = g.fromJson(oRes.getBody(), GoogleUporabnik.class);
            Uporabnik noviUporabnik = uporabnikVmesnik.pridobiPoApiId(googleUporabnik.getId());

            if(noviUporabnik == null){
                noviUporabnik = new UporabnikBuilder()
                        .setEmail(googleUporabnik.getEmail())
                        .setIme(googleUporabnik.getGiven_name())
                        .setPriimek(googleUporabnik.getFamily_name())
                        .setIdApi(googleUporabnik.getId())
                        .setTip(0)
                        .setDatumKreacije(Calendar.getInstance())
                        .setPosta(0)
                        .createUporabnik();

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



        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession(true);
            request.getRequestDispatcher("index.xhtml").forward(request, response);
        }

    }
}
