package com.paypal.bfs.test.employeeserv.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Optional;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "ADDRESS")
@Data
@NoArgsConstructor
public class Address implements Serializable {

    private static final long serialVersionUID = -4928161095186435377L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ADDRESS_ID", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "LINE1")
    private String line1;

    @Column(name = "LINE2")
    private String line2;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "address")
    private Employee employee;
}
