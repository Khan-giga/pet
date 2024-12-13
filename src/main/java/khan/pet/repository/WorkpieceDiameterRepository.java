package khan.pet.repository;

import jakarta.persistence.NoResultException;
import khan.pet.entity.WorkpieceDiameter;
import khan.pet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;

public class WorkpieceDiameterRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Optional<WorkpieceDiameter> findWorkpieceDiameterByDiameter(Integer diameter) {
        WorkpieceDiameter workpieceDiameter = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<WorkpieceDiameter> query = session.createQuery(
                    "from WorkpieceDiameter w where w.milimetersDiameter = :diameter", WorkpieceDiameter.class);
            query.setParameter("diameter", diameter);
            workpieceDiameter = query.getSingleResult();
            transaction.commit();
        } catch (NoResultException e) {
            workpieceDiameter = null;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(workpieceDiameter);
    }

}
