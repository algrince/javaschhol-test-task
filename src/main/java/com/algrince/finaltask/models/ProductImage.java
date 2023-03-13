package com.algrince.finaltask.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @OneToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

}
