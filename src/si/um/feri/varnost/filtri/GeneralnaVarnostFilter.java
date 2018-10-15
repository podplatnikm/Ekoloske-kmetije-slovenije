package si.um.feri.varnost.filtri;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Miha on 12/06/2017.
 */
@WebFilter(filterName = "GeneralnaVarnostFilter",
urlPatterns = {"/izpis"})
public class GeneralnaVarnostFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        System.out.println("VANOST: Generalna Varnost");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String vpisURI = request.getContextPath() + "/index.xhtml";

        boolean vpisan = session != null && session.getAttribute("uporabnik") != null;

        if(vpisan){
            chain.doFilter(req, resp);
        }else{
            response.sendRedirect(vpisURI);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
