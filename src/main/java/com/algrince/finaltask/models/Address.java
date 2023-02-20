package com.algrince.finaltask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    @Size(min = 5, max = 6)
    private int postalCode;

    @Column(name = "street")
    @NotEmpty
    private String street;

    @Column(name = "home")
    @NotEmpty
    private int home;

    @Column(name = "apartment")
    @NotEmpty
    private int apartment;

    @Column(name = "door")
    @Size(max = 1)
    private String door;

    @Column(name = "deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;
}
