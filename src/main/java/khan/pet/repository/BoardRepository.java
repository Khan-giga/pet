package khan.pet.repository;

import khan.pet.entity.Board;
import khan.pet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class BoardRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveBoard(Board board) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(board);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}
