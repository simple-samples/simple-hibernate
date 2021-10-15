package models;

import javax.persistence.*;

@Entity
@Table(name="cars")
public class Car {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer year;

    @Column
    private String make;

    @Column
    private String model;

    @Column
    private String color;


    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable=false)
    private Garage garage;

    public Car() {

    }


    public Car(Integer year, String make, String model, String color) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
