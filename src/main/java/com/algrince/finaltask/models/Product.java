package com.algrince.finaltask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty
    private String title;

    @Column(name = "price")
    @NotNull
    private Double price;

    @Column(name = "stock")
    @NotNull
    private int stock;

    @Column(name = "volume")
    @NotNull
    private Double volume;

    @Column(name = "weight")
    @NotNull
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "product")
    private ProductImage productImage;

    @Column(name = "deleted")
    private boolean isDeleted;
}
