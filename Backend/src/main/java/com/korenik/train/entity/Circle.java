package com.korenik.train.entity;

import com.korenik.train.model.BorderStyle;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Entity(name = "circles")
public class Circle extends Figure {

    @Enumerated(EnumType.STRING)
    private BorderStyle borderStyle;

    public Circle() {
        this.borderStyle = BorderStyle.SOLID;
    }

}
