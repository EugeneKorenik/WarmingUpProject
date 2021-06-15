package com.korenik.train.entity;

import com.korenik.train.model.GroupType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "groups")
public class Group extends BaseEntity {

    private GroupType groupType;

    @OneToOne
    private Picture picture;

    @ManyToOne
    @JoinColumn(name = "parent_group_id")
    private Group parentGroup;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_group_id")}
    )
    private List<Group> groups;

    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL)
    private List<Figure> figures;

}
