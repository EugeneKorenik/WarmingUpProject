package com.korenik.train.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Figure extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group parentGroup;

}
