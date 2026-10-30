<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Identification</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1>Entrer dans la boutique</h1>

<c:if test="${not empty erreur}">
    <p style="color: red"><c:out value="${erreur}"/></p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/internaute">
    <label>
        Nom :
        <input type="text" name="nom" required>
    </label>

    <br><br>

    <label>
        E-mail :
        <input type="email" name="email" required>
    </label>

    <br><br>

    <button type="submit">Continuer vers la vitrine</button>
</form>
</body>
</html>