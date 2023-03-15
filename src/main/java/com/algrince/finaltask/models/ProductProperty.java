package com.algrince.finaltask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @ManyToMany
    @JoinTable(
            name = "product_has_property_value",
            joinColumns = @JoinColumn(name = "product_property_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Column(name = "value")
    @NotEmpty
    private String propertyValue;

    @Column(name = "deleted")
    private boolean isDeleted;
}
