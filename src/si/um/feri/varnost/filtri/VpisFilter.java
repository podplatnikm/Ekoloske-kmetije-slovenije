/*package si.um.feri.varnost.filtri;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Miha on 22/05/2017.
 */
/*@WebFilter(urlPatterns = {"/uporabnik/*"})
public class VpisFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("VARNOST: V Filtru za Vpis!");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String vpisURI = request.getContextPath() + "/index.xhtml";

        //boolean vpisan = session != null && session.getAttribute("uporabnik") != null;
        boolean vpisan = session.getAttribute("uporabnik") != null ? true : false;
        boolean seja = session == null ? false : true;

        if(vpisan && seja){
            chain.doFilter(req, resp);
        }else{
            response.sendRedirect(vpisURI);
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}*/
