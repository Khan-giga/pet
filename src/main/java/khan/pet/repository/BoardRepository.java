package khan.pet.repository;

import khan.pet.entity.Board;
import khan.pet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Map;
import java.util.stream.Collectors;

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

    public void deleteByType(String type) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(
                    "DELETE FROM boards WHERE wood_types_id IN " +
                    "(SELECT id FROM wood_types WHERE name = :type)")
                    .setParameter("type", type)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Map<String, Long> findQuantityByType() {
        Transaction transaction = null;
        Map<String, Long> map = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Object[]> query = session.createQuery("select b.woodTypes.name as type, count(b) from Board b " +
                                                     "group by b.woodTypes.name", Object[].class);
            map = query.getResultStream()
                    .collect(Collectors.toMap(
                            res -> (String) res[0],
                            res -> (Long) res[1]
                    ));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return map;

    }

}
