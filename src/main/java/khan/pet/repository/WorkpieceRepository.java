package khan.pet.repository;

import khan.pet.entity.Board;
import khan.pet.entity.WoodType;
import khan.pet.entity.Workpiece;
import khan.pet.entity.WorkpieceDiameter;
import khan.pet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class WorkpieceRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Workpiece saveWorkpiece(WoodType woodType, WorkpieceDiameter workpieceDiameter, Long length) {
        Transaction transaction = null;
        Workpiece workpiece = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            workpiece = Workpiece.builder()
                    .woodTypes(woodType)
                    .metersLength(length)
                    .workpieceDiameter(workpieceDiameter)
                    .build();
            session.persist(workpiece);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return workpiece;

    }

}
