package com.algrince.finaltask.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "property_values")
@Getter
@Setter
@NoArgsConstructor
public class ProductProperty {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;


    @ManyToMany(mappedBy = "propertyValues")

    private List<Product> products;

    @Column(name = "value")
    @NotEmpty
    private String propertyValue;

    @Column(name = "deleted")
    private boolean isDeleted;
}
