package com.potilati.productService.DTO;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {

    private double reference;

    @Size(max = 60)
    private String name;

    private String description;

    public ProductDTO() {

    }

    public ProductDTO(double reference) {
        this.reference = reference;
    }

    public ProductDTO(double reference, String name, String description) {
        this.reference = reference;
        this.name = name;
        this.description = description;
    }

}
