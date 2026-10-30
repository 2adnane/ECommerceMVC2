package ma.fstt.servlet;

import ma.fstt.entities.Internaute;
import ma.fstt.entities.Panier;
import ma.fstt.service.LignePanierService;
import ma.fstt.service.PanierService;
import ma.fstt.service.impl.LignePanierServiceJPA;
import ma.fstt.service.impl.PanierServiceJPA;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/panier")
public class PanierServlet extends HttpServlet {

    private PanierService panierService;
    private LignePanierService ligneService;

    @Override
    public void init() {
        panierService = new PanierServiceJPA();
        ligneService = new LignePanierServiceJPA();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Internaute internaute =
                (Internaute) request.getSession().getAttribute("internaute");

        if (internaute == null) {
            response.sendRedirect(request.getContextPath() + "/internaute");
            return;
        }

        Panier panier = panierService.trouverOuCreerPour(internaute);
        request.setAttribute("panier", panier);
        request.setAttribute("lignes", ligneService.trouverParPanier(panier.getId()));

        request.getRequestDispatcher("/WEB-INF/views/panier/liste.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Internaute internaute =
                (Internaute) request.getSession().getAttribute("internaute");

        if (internaute == null) {
            response.sendRedirect(request.getContextPath() + "/internaute");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("ajouter".equals(action)) {
                Long produitId = Long.valueOf(request.getParameter("produitId"));
                int quantite = Integer.parseInt(request.getParameter("quantite"));
                Panier panier = panierService.trouverOuCreerPour(internaute);
                ligneService.ajouterProduit(panier.getId(), produitId, quantite);

            } else if ("modifier".equals(action)) {
                Long ligneId = Long.valueOf(request.getParameter("ligneId"));
                int quantite = Integer.parseInt(request.getParameter("quantite"));
                ligneService.changerQuantite(ligneId, quantite);

            } else if ("supprimer".equals(action)) {
                Long ligneId = Long.valueOf(request.getParameter("ligneId"));
                ligneService.supprimer(ligneId);
            }else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Action de panier inconnue"
                );
                return;
            }

            response.sendRedirect(request.getContextPath() + "/panier");

        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("erreurPanier", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/panier");
        }
    }
}