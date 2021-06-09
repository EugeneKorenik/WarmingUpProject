package com.korenik.train.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity(name = "pictures")
public class Picture extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "picture", cascade = CascadeType.ALL)
    private List<Group> groups;

}
