package com.ascending.training.model;


import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "balance")
    private float balance;

    @Column(name = "employee_id")
    private int employeeId;




}
