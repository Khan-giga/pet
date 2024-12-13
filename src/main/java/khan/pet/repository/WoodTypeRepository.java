package khan.pet.repository;

import jakarta.persistence.NoResultException;
import khan.pet.entity.WoodType;
import khan.pet.exception.UnknownWoodException;
import khan.pet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
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
            woodType = Optional.ofNullable(query.getSingleResult()).orElseThrow(UnknownWoodException::new);
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

    public List<WoodType> findAll() {
        Transaction transaction = null;
        List<WoodType> woodTypes = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<WoodType> query = session.createQuery("from WoodType", WoodType.class);
            woodTypes = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

        return woodTypes;

    }

    public void createWoodType(WoodType woodType) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(woodType);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

    public void updateWoodType(String name, String updatedName) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<WoodType> query = session.createQuery("from WoodType w where w.name = :name", WoodType.class);
            query.setParameter("name", name);
            WoodType woodType = Optional.ofNullable(query.getSingleResult()).orElseThrow(UnknownWoodException::new);
            woodType.setName(updatedName);
            session.persist(woodType);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void deleteWoodTypeByName(String name) {
        Transaction transaction = null;
        WoodType woodType = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<WoodType> query = session.createQuery("from WoodType w where w.name = :name", WoodType.class);
            query.setParameter("name", name);
            woodType = Optional.ofNullable(query.getSingleResult()).orElseThrow(UnknownWoodException::new);
            session.remove(woodType);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

}
