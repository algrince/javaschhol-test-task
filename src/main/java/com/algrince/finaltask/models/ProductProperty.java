package com.algrince.finaltask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.List;

@Entity
@Table(name = "property_values")
@Getter
@Setter
@NoArgsConstructor
@FilterDef(name = "deletedProductPropertyFilter", parameters = @ParamDef(
        name = "isDeleted",
        type = org.hibernate.type.descriptor.java.BooleanJavaType.class))
@Filter(name = "deletedProductPropertyFilter", condition = "deleted = :isDeleted")
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

    @Column(name = "value", unique = true)
    @NotEmpty
    private String propertyValue;

    @Column(name = "deleted")
    private boolean isDeleted;
}
