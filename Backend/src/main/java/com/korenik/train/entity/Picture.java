package com.korenik.train.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "pictures")
public class Picture extends BaseEntity {

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "root_group_id")
    private Group rootGroup;

}
