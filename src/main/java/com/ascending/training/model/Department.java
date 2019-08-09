package com.ascending.training.model;

import javax.persistence.*;


@Entity
@Table(name="department")
public class Department {

    public Department() {}

    @Id //mark primary key
//    @SequenceGenerator(name = "department_id_generator", sequenceName = "department_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = SEQUENCE, generator = "department_id_generator")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //to use this, id has to be SERIAL type in sql script
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }


}
