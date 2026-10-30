<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mon panier</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<a href="${pageContext.request.contextPath}/internaute?action=deconnexion">
    Se déconnecter
</a>

<h1>Mon panier</h1>

<c:if test="${not empty sessionScope.erreurPanier}">
    <p style="color:red"><c:out value="${sessionScope.erreurPanier}"/></p>
    <c:remove var="erreurPanier" scope="session"/>
</c:if>

<p><a href="${pageContext.request.contextPath}/produits">Continuer les achats</a></p>

<c:choose>
    <c:when test="${empty lignes}">
        <p>Votre panier est vide.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="8">
            <tr>
                <th>Produit</th><th>Prix unitaire</th>
                <th>Quantité</th><th>Total</th><th>Actions</th>
            </tr>
            <c:forEach var="ligne" items="${lignes}">
                <tr>
                    <td><c:out value="${ligne.produit.nom}"/></td>
                    <td><c:out value="${ligne.prixUnitaire}"/> DH</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/panier">
                            <input type="hidden" name="action" value="modifier">
                            <input type="hidden" name="ligneId" value="${ligne.id}">
                            <input type="number" name="quantite" min="1"
                                   value="${ligne.quantite}" required>
                            <button type="submit">Modifier</button>
                        </form>
                    </td>
                    <td>${ligne.prixUnitaire * ligne.quantite} DH</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/panier">
                            <input type="hidden" name="action" value="supprimer">
                            <input type="hidden" name="ligneId" value="${ligne.id}">
                            <button type="submit">Retirer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>