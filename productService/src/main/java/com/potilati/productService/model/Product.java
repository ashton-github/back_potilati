package com.potilati.productService.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "product",
        uniqueConstraints = {@UniqueConstraint(columnNames = "reference")}
)
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private double reference;


    @Size(max = 60)
    private String name;

    private String description;

    public Product() {

    }

    public Product(double reference) {
        this.reference = reference;
    }

    public Product(double reference, String name, String description) {
        this.reference = reference;
        this.name = name;
        this.description = description;
    }
}
