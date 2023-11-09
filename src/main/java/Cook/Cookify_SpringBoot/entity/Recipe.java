package Cook.Cookify_SpringBoot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Recipe extends  BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    private String title;

    private String Content;

    @ManyToOne
    private GoogleMember googleMember;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();



}
