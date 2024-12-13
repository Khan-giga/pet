package khan.pet.Service;

import khan.pet.dto.request.Blank;
import khan.pet.dto.response.ResponseDto;
import khan.pet.entity.Board;
import khan.pet.entity.Workpiece;
import khan.pet.repository.WoodTypeRepository;
import khan.pet.repository.WorkpieceRepository;
import khan.pet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SawmillTest {

    private static List<Blank> blanks;
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void setUp() {
        blanks = new ArrayList<>(Arrays.asList(
                new Blank("Дуб", 500, 14L),
                new Blank("Дуб", 200, 14L),
                new Blank("Ель", 500, 7L),
                new Blank("Сосна", 500, 10L)));
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @AfterEach
    void clearTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("truncate table boards, workpieces cascade").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Test
    void getTypeBlankAndCountPlanksFromListBlanks() {

        Map<String, Long> map = Sawmill.calculatePlanksForDb(blanks);

        assertNotEquals(98, map.get("Дуб"));

    }

    @Test
    void negativeGetTypeBlankAndCountPlanksFromListBlanks() {

        Map<String, Long> map = Sawmill.calculatePlanksForDb(blanks);

        assertNotEquals(44, map.get("Дуб"));

    }

    @Test
    void getEmptyResultWhereListIsEmpty() {
        List<Blank> blanks = new ArrayList<>();
        Map<String, Long> map = Sawmill.calculatePlanksForDb(blanks);

        assertTrue(map.isEmpty());

    }

    @Test
    void calculatePlanksForDbWithNullBlanks(){
        Map<String, Long> map = new HashMap<>();

        assertEquals(map, Sawmill.calculatePlanksForDb(null));

    }

}