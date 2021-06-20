package com.korenik.train.entity;

import com.korenik.train.model.GroupType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "groups")
public class Group extends Figure {

    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @OneToOne
    private Picture picture;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "group_figure",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "figure_id")}
    )
    private List<Figure> figures;

    public Group() {
        this.groupType = GroupType.ROW;
    }

}
