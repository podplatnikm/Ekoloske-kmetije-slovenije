/*package si.um.feri.varnost.filtri;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Miha on 31/05/2017.
 */
/*@WebFilter(/*urlPatterns = {"/facebookVpis", "/googleplusVpis", "/FacebookOAuth2callback", "/GoogleOAuth2callback"}*//*)*/
/*public class DvojniVpisLogin implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("VARNOST: V Filtru za Dvojni Vpis!");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String URI = request.getContextPath() + "/vnos-izdelkov.xhtml";

        //chain.doFilter(req, resp);

        boolean vpisan = session.getAttribute("uporabnik") != null;

        //System.out.println(vpisan);

        if(!vpisan){
            chain.doFilter(req, resp);
        }else{
            response.sendRedirect(URI);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}*/
