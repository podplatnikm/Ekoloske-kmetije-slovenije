package si.um.feri.varnost;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;
import si.um.feri.varnost.apiji.Google2Api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Miha on 21/05/2017.
 */
@WebServlet("/googleplusVpis")
public class googleplusVpis extends HttpServlet {

    private static final String CLIENT_ID = "479163532801-avobgg5fbpgpnt1sshenb6ic4gfk95rc.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "mNXmijQepiefe3LO4qVo3LRD";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceBuilder builder = new ServiceBuilder();
        OAuthService service = builder
                .provider(Google2Api.class)
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback("http://localhost:8080/praktikum2_intelliJ_zadnjiWeb/GoogleOAuth2callback")
                .scope("openid profile email")
                .debugStream(System.out)
                .build();

        HttpSession session = request.getSession();
        session.setAttribute("oauth2Service", service);

        response.sendRedirect(service.getAuthorizationUrl(null));

    }
}
