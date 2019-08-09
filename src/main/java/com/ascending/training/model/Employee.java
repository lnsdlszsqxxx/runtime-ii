package com.ascending.training.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee {

    public Employee(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //to use this, id has to be SERIAL type in sql script
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String  email;

    @Column(name = "address")
    private String  address;

    @Column(name = "department_id")
    private int department_id;

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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", department_id=" + department_id +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                department_id == employee.department_id &&
                name.equals(employee.name) &&
                first_name.equals(employee.first_name) &&
                last_name.equals(employee.last_name) &&
                email.equals(employee.email) &&
                address.equals(employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, first_name, last_name, email, address, department_id);
    }
}


