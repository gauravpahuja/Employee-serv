package com.paypal.bfs.test.employeeserv.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private Integer id;

    private String line1;

    private String line2;

    private String city;

    private String state;

    private String country;

    private String  zipCode;
}
