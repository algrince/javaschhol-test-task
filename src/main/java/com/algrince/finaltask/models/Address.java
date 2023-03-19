package com.algrince.finaltask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country")
    @NotEmpty
    private String country;

    @Column(name = "city")
    @NotEmpty
    private String city;

    @Column(name = "postal_code")
    @NotNull
    // @Size(min = 5, max = 6)
    private int postalCode;

    @Column(name = "street")
    @NotEmpty
    private String street;

    @Column(name = "home")
    @NotNull
    private int home;

    @Column(name = "apartment")
    @NotNull
    private int apartment;

    @Column(name = "door")
    @Size(max = 1)
    private String door;

    @Column(name = "deleted")
    private boolean isDeleted;

    // Column name is due to the current naming of MySQL database
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private User owner;
}
