package com.korenik.train.entity;

import com.korenik.train.model.BorderType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "circles")
public class Circle extends Figure {

    @NotNull
    private BorderType borderType;

}
