package com.korenik.train.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "triangles")
public class Triangle extends Figure {

    private String colorHex;

}
