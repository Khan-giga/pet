package khan.pet.repository;

import jakarta.persistence.NoResultException;
import khan.pet.entity.WoodType;
import khan.pet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;

public class WoodTypeRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Optional<WoodType> findWoodTypeByName(String name) {
        WoodType woodType = null;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<WoodType> query = session.createQuery("from WoodType w where w.name = :name", WoodType.class);
            query.setParameter("name", name);
            woodType = query.getSingleResult();
            transaction.commit();
        } catch (NoResultException e) {
            woodType = null;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return Optional.ofNullable(woodType);

    }

}
