package si.um.feri.varnost;


import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;
import si.um.feri.varnost.apiji.FacebookCustomApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Miha on 30/05/2017.
 */
@WebServlet("/facebookVpis")
public class facebookVpis extends HttpServlet {

    private static final String CLIENT_ID = "1891418244450297";
    private static final String CLIENT_SECRET = "2560f455e06d2e47ad8ab92a5c9195dd";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("test");

        OAuthService service = new ServiceBuilder()
                .provider(FacebookCustomApi.class)
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback("http://localhost:8080/praktikum2_intelliJ_zadnjiWeb/FacebookOAuth2callback")
                .scope("public_profile,email")
                .debugStream(System.out)
                .build();


        HttpSession session = request.getSession();
        session.setAttribute("oauth2Service", service);

        response.sendRedirect(service.getAuthorizationUrl(null));


    }
}
