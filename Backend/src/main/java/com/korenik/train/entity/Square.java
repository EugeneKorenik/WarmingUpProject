package com.korenik.train.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Entity(name = "squares")
public class Square extends Figure {

    @NotNull
    @Pattern(regexp = "^([A-Z]|[0-9])$")
    private String symbol;

}
