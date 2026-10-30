package ma.fstt.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JPAUtil {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("ecommercePU");

    private JPAUtil() {
    }

    public static EntityManagerFactory getFactory() {
        return FACTORY;
    }

    public static void fermer() {
        if (FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}