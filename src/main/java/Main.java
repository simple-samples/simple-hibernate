import models.Car;
import models.Garage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Set up the persistence context
        Configuration config = new Configuration().configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Car.class);
        config.addAnnotatedClass(Garage.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();

        //Build some objects to persist
        Garage garage = new Garage();
        garage.setDriver("Kyle");
        Car carA = new Car(1991, "Geo", "Metro", "red");
        garage.parkCar(carA);
        Car carB = new Car(1998, "Ford", "Taurus", "green");
        garage.parkCar(carB);
        Car carC = new Car(2008, "Honda", "Civic", "red");
        garage.parkCar(carC);

        //After this the cars and garage are in persistent state. As long as the session is open updates to
        //these objects are reflected in the database
        Transaction transaction = session.beginTransaction();
        Integer garageId = (Integer)session.save(garage);
        session.save(carA);
        session.save(carB);
        session.save(carC);
        transaction.commit();


        //these changes are reflected in the database as hibernate keeps the db synced with its caches
        transaction = session.beginTransaction();
        garage.getCarList().get(0).setColor("purple");
        transaction.commit();



        //We can simply get items out of the database
        Garage getGarage = session.get(Garage.class, garageId);
        System.out.println("ALL CARS:");
        for(Car car : getGarage.getCarList()) {
            System.out.println(car.getColor() + " " + car.getMake() + " " + car.getModel());
        }

        //using the criteria API to get red cars
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);
        query.select(root).where(builder.equal(root.get("color"), "red"));
        List<Car> redCars = session.createQuery(query).getResultList();
        System.out.println("RED CARS:");
        for(Car car : redCars) {
            System.out.println(car.getColor() + " " + car.getMake() + " " + car.getModel());
        }


        //re-assigning the references above, and using the same method again to build
        //a query for finding fords built before 2000
        query = builder.createQuery(Car.class);
        root = query.from(Car.class);
        query.select(root).where(builder.and(builder.equal(root.get("make"), "Ford"), builder.lt(root.get("year"), 2000)));
        List<Car> oldFords = session.createQuery(query).getResultList();
        System.out.println("OLD FORDS:");
        for(Car car : oldFords) {
            System.out.println(car.getColor() + " " + car.getMake() + " " + car.getModel());
        }





        //close the session
        session.close();
        sessionFactory.close();

        //start a new session
        config = new Configuration().configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Car.class);
        config.addAnnotatedClass(Garage.class);
        sessionFactory = config.buildSessionFactory();
        session = sessionFactory.openSession();


        //Make sure our items are properly accesible from a new session
        Garage kylesGarage = session.get(Garage.class, 1);
        System.out.println("ALL CARS:");
        for(Car car : kylesGarage.getCarList()) {
            System.out.println(car.getColor() + " " + car.getMake() + " " + car.getModel());
        }


        //close the session again
        session.close();
        sessionFactory.close();


    }
}
