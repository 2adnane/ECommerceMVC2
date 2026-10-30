package ma.fstt.servlet;

import ma.fstt.entities.Internaute;
import ma.fstt.service.InternauteService;
import ma.fstt.service.impl.InternauteServiceJPA;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/internaute")
public class InternauteServlet extends HttpServlet {

    private InternauteService internauteService;

    @Override
    public void init() {
        internauteService = new InternauteServiceJPA();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("deconnexion".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/produits");
            return;
        }

        HttpSession session = request.getSession();
        Internaute internaute = (Internaute) session.getAttribute("internaute");

        if (internaute == null) {
            request.getRequestDispatcher("/WEB-INF/views/internaute/identification.jsp")
                    .forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/produits");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nom = request.getParameter("nom");
        String email = request.getParameter("email");

        if (nom == null || nom.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            request.setAttribute("erreur", "Le nom et l’e-mail sont obligatoires.");
            request.getRequestDispatcher("/WEB-INF/views/internaute/identification.jsp")
                    .forward(request, response);
            return;
        }

        Internaute internaute = internauteService.trouverParEmail(email.trim());

        if (internaute == null) {
            // Compte de démonstration pour l’exercice.
            internaute = new Internaute(nom.trim(), email.trim(), "demo");
            internauteService.ajouter(internaute);
        }

        request.getSession().setAttribute("internaute", internaute);
        response.sendRedirect(request.getContextPath() + "/produits");
    }
}