package ma.fstt;

import ma.fstt.util.JPAUtil;
import javax.persistence.EntityManager;

public class TestJPA {

    public static void main(String[] args) {
        EntityManager em = null;

        try {
            em = JPAUtil.getFactory().createEntityManager();
            System.out.println("JPA est connecté à la base.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            JPAUtil.fermer();
        }
    }
}