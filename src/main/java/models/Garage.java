package models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="garages")
public class Garage {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy="garage")
    private List<Car> carList = new LinkedList<>();

    @Column
    private String driver;


    public Garage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void parkCar(Car car) {
        carList.add(car);
        car.setGarage(this);
    }
}
