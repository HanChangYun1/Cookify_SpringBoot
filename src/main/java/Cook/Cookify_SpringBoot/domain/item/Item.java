package Cook.Cookify_SpringBoot.domain.item;

import Cook.Cookify_SpringBoot.domain.category.Category;
import Cook.Cookify_SpringBoot.domain.item.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    //생성 메서드//
    public static Item createItem(String name, int price, int stockQuantity){
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        return item;

    }


    //비즈니스로직//
    public void addStock(int stockQuantity){this.stockQuantity += stockQuantity;}

    public void removeStock(int stockQuantity){
        int restStock = this.stockQuantity -=stockQuantity;
        if (restStock < 0){
            throw new NotEnoughStockException("재고가 부족합니다");
        }
        this.stockQuantity = restStock;
    }

    //연관관계 메서드//
    public void addCategory(Category category){
        this.categories.add(category);
    }
}
