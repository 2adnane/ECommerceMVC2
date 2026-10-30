# Application e-commerce MVC 2 et JPA

Application Web pédagogique réalisée dans le cadre du module JEE à la Faculté des Sciences et Techniques de Tanger. Elle présente une vitrine de produits et permet à un internaute de gérer son panier.

## Objectifs

- Utiliser JPA avec EclipseLink pour enregistrer et lire des objets Java dans MySQL.
- Organiser l’application avec le modèle MVC 2 : les Servlets contrôlent les requêtes, les services exécutent les opérations et les JSP affichent les résultats.
- Mettre en œuvre la vitrine, l’identification de démonstration et les opérations principales du panier.

## Fonctionnalités

- Afficher les produits et leur stock.
- Identifier un internaute à l’aide de son nom et de son adresse e-mail.
- Créer ou retrouver son panier.
- Ajouter un produit au panier.
- Modifier la quantité d’un article.
- Retirer un article du panier.
- Refuser une quantité invalide ou supérieure au stock.

> L’identification est une démonstration pédagogique. Elle ne vérifie pas un mot de passe et ne doit pas être utilisée comme authentification pour une application réelle.

## Technologies

- Java 17
- Maven
- Jakarta/Java EE Servlet API (`javax.servlet`, Servlet 4)
- JSP et JSTL
- JPA 2.2 avec EclipseLink
- MySQL
- Apache Tomcat 9
- IntelliJ IDEA

## Architecture

```text
src/main/java/ma/fstt/
├── entities/       Entités JPA : Internaute, Produit, Panier, LignePanier
├── service/        Interfaces des services
├── service/impl/   Opérations métier avec EntityManager et transactions
├── servlet/        Contrôleurs Web
└── util/           JPAUtil et gestion de l'EntityManagerFactory

src/main/resources/META-INF/
└── persistence.xml  Configuration JPA et connexion MySQL

src/main/webapp/
├── css/             Feuilles de style
└── WEB-INF/views/   Pages JSP de la vitrine, de l'identification et du panier