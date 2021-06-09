package com.korenik.train.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "squares")
public class Square extends Figure {

    private char symbol;

}
