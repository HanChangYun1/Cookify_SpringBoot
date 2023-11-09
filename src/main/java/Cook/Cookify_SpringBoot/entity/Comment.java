package Cook.Cookify_SpringBoot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private GoogleMember member;

    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private Comment parent;

    @OneToMany
    private List<Comment> children = new ArrayList<>();
}
