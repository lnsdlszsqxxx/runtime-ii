package com.ascending.training.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ascending.training.model.Student;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "accounts")
@JsonInclude(value = JsonInclude.Include.NON_NULL) //don't show null value
public class Account implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "balance")
    private float balance;


//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;


    public Long getId() {
        return id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Student getStudent() {

        System.out.println(Counter.counterStAcc);
        if(Counter.counterStAcc==0) {
            try {
                String add = student.getAddress();
            } catch (Exception e) {
                return null;
            }
            Counter.counterAccSt+=1;
            System.out.println("ACCST: "+Counter.counterAccSt);
            return student;
        }
        else {return null;}
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", student=" + student.getId() +
                '}';
    }
}
