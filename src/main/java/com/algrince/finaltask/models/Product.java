package com.algrince.finaltask.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;


@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(
        name = "isDeleted",
        type = org.hibernate.type.descriptor.java.BooleanJavaType.class))
@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "deleted")
    private boolean isDeleted;
}
