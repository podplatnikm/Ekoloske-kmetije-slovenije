package si.um.feri.varnost.filtri;

import si.um.feri.uporabnik.UporabnikBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Miha on 12/06/2017.
 */
@WebFilter(filterName = "StraniRegistracijeFilter",
urlPatterns = {"/registracijaKmetije.xhtml", "/registracijaUporabnika.xhtml",})
public class StraniRegistracijeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String vpisURI = request.getContextPath() + "/index.xhtml";

        UporabnikBean bean = (UporabnikBean) session.getAttribute("uporabnikBean");

        boolean vpisan = session.getAttribute("uporabnik") != null
                && session != null
                && bean.getUporabnik().getPosta() == 0;
        if(vpisan){
            chain.doFilter(req, resp);
        }else{
            response.sendRedirect(vpisURI);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
