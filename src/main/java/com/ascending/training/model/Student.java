package com.ascending.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
@JsonInclude(value = JsonInclude.Include.NON_NULL) //ignore null value.
public class Student{

    public Student(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //to use this, id has to be SERIAL type in sql script
    private Long id;

    @Column(name = "name")
    private String stName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String  email;


    @Column(name = "address")
    private String  address;

//    @Column(name = "department_id")
//    private int department_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;


    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Account> accounts;


    public Long getId() {
        return id;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String name) {
        this.stName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

//    public Set<Account> getAccounts() {
//        return accounts;
//    }

    public Set<Account> getAccounts() {
        if(Counter.counterAccSt==0) {
            try {
                int size = accounts.size();
            } //no particular purpose, just try to use accounts
            //When accounts is accessed, Hibernate will try to get the data (LAZY).
            //But at this time, session is closed, so there will be an exception.
            //As a result:
            //if accounts doesn't exit (not fetched by HQL), it throws an exception. Then in catch, it returns null;
            //if accounts does exit (when join fetch is used), nothing is changed. It will return accounts at the end.
            catch (Exception e) {
                return null;
            }

            Counter.counterStAcc += 1;
            return accounts;
        }
        else {return null;}
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + stName + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
 //               ", department_id=" + department_id +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id &&
  //              department_id == student.department_id &&
                stName.equals(student.stName) &&
                firstName.equals(student.firstName) &&
                lastName.equals(student.lastName) &&
                email.equals(student.email) &&
                address.equals(student.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stName, firstName, lastName, email, address);
    }
}


