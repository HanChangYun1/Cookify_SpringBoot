package Cook.Cookify_SpringBoot.domain.category;

import Cook.Cookify_SpringBoot.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    //연관관계 메서드//
    public void confirmParent(Category parent){
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(Category child){child.addChild(child);}

}
