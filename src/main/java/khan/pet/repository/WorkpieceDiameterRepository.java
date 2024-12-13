package khan.pet.repository;

import jakarta.persistence.NoResultException;
import khan.pet.entity.WoodType;
import khan.pet.entity.WorkpieceDiameter;
import khan.pet.exception.UnknownWoodException;
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

    public void createWorkPieceDiameter(WorkpieceDiameter workpieceDiameter) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(workpieceDiameter);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public void deleteWorkPieceDiameter(Integer diameter) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(
                            "DELETE FROM workpiece_diameter WHERE milimeters_diameter = :diameter")
                    .setParameter("diameter", diameter)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void updateWorkPieceDiameter(Integer diameter, Integer updateDiameter, Integer boardCount) {
        Transaction transaction = null;
        WorkpieceDiameter workpieceDiameter = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<WorkpieceDiameter> query = session.createQuery("from WorkpieceDiameter w where w.milimetersDiameter = :diameter", WorkpieceDiameter.class);
            query.setParameter("diameter", diameter);
            workpieceDiameter = Optional.ofNullable(query.getSingleResult()).orElseThrow(UnknownWoodException::new);
            workpieceDiameter.setMilimetersDiameter(updateDiameter);
            workpieceDiameter.setBoardCount(boardCount);
            session.persist(workpieceDiameter);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

}
