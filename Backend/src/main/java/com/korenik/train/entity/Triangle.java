package com.korenik.train.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Entity(name = "triangles")
public class Triangle extends Figure {

    @Pattern(regexp = "^#([A-F]|[a-f]|[0-9]){6}$")
    private String colorHex;

    public Triangle() {
        this.colorHex = "#c2c2c2";
    }

}
