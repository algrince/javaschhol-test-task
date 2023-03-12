/* package com.algrince.finaltask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_has_property")
@Getter
@Setter
@NoArgsConstructor
public class ProductProperty {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_id")
    @NotNull
    private Property property;

    @Column(name = "value")
    @NotEmpty
    private String propertyValue;

    @Column(name = "deleted")
    private boolean isDeleted;
}
*/