package persistence;

import models.Car;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class CarRepository {
    private static Session session;

    public static void init(Session s) {
        session = s;
    }


    public static List<Car> getAllCars() {
        CriteriaBuilder builder = session.getCriteriaBuilder();


        return null;
    }
}
