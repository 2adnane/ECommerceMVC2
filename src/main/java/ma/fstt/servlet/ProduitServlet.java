package ma.fstt.servlet;

import ma.fstt.entities.Produit;
import java.util.List;
import ma.fstt.service.ProduitService;
import ma.fstt.service.impl.ProduitServiceJPA;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private ProduitService produitService;

    @Override
    public void init() {
        produitService = new ProduitServiceJPA();

        // Insère les exemples seulement si la table produit est vide.
        if (produitService.trouverTous().isEmpty()) {
            List<Produit> telephones = List.of(
                    new Produit("Samsung Galaxy A55", "Écran AMOLED, 128 Go, 8 Go RAM", 4299.00, 12),
                    new Produit("iPhone 15", "Écran Super Retina, 128 Go", 8999.00, 8),
                    new Produit("Xiaomi Redmi Note 13", "Écran AMOLED, 256 Go, 8 Go RAM", 2499.00, 15),
                    new Produit("Samsung Galaxy S24", "Écran Dynamic AMOLED, 256 Go", 9999.00, 5),
                    new Produit("OPPO Reno 11", "Écran AMOLED, 256 Go, 8 Go RAM", 3799.00, 9)
            );

            for (Produit produit : telephones) {
                produitService.ajouter(produit);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("produits", produitService.trouverTous());

        request.getRequestDispatcher("/WEB-INF/views/produits/liste.jsp")
                .forward(request, response);
    }
}