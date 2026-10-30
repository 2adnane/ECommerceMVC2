<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Vitrine des produits</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<h1>Vitrine des produits</h1>

<c:choose>
    <c:when test="${not empty sessionScope.internaute}">
        <p>
            Connecté en tant que
            <c:out value="${sessionScope.internaute.nom}"/>
            — <a href="${pageContext.request.contextPath}/internaute?action=deconnexion">
                Se déconnecter
            </a>
        </p>
    </c:when>
    <c:otherwise>
        <p>
            <a href="${pageContext.request.contextPath}/internaute">
                S’identifier
            </a>
        </p>
    </c:otherwise>
</c:choose>

<p>
    <a href="${pageContext.request.contextPath}/panier">Voir mon panier</a>
</p>

<c:choose>
    <c:when test="${empty produits}">
        <p>Aucun produit disponible pour le moment.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="8">
            <thead>
            <tr>
                <th>Nom</th>
                <th>Description</th>
                <th>Prix</th>
                <th>Stock</th>
                <th>Panier</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="produit" items="${produits}">
                <tr>
                    <td><c:out value="${produit.nom}"/></td>
                    <td><c:out value="${produit.description}"/></td>
                    <td><c:out value="${produit.prix}"/> DH</td>
                    <td><c:out value="${produit.stock}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${produit.stock > 0}">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/panier">
                                    <input type="hidden" name="action" value="ajouter">
                                    <input type="hidden" name="produitId"
                                           value="${produit.id}">
                                    <input type="number" name="quantite"
                                           value="1" min="1"
                                           max="${produit.stock}" required>
                                    <button type="submit">Ajouter</button>
                                </form>
                            </c:when>
                            <c:otherwise>Indisponible</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>