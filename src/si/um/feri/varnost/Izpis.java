package si.um.feri.varnost;

import com.google.gson.Gson;
import si.um.feri.uporabnik.Uporabnik;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Miha on 23/05/2017.
 */
@WebServlet("/izpis")
public class Izpis extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String token = (String) request.getSession().getAttribute("token").toString();
            Uporabnik uporabnik = (Uporabnik) request.getSession().getAttribute("uporabnik");


                HttpSession session = request.getSession();
                session.removeAttribute("token");
                session.removeAttribute("uporabnik");
                session.invalidate();

                System.out.println("VARNOST: Izpis uporabnika!");
                request.getRequestDispatcher("index.xhtml").forward(request, response);


        } catch (Exception e){
            System.out.println("VARNOST: Uporabnik ni vpisan!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(new Gson().toJson("VARNOST: Uporabnik ni vpisan!"));
            return;

        }

    }
}
