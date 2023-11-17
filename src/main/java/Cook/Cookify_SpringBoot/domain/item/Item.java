package Cook.Cookify_SpringBoot.domain.item;

import Cook.Cookify_SpringBoot.domain.item.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    //비즈니스로직//
    public void addStock(int stockQuantity){this.stockQuantity += stockQuantity;}

    public void removeStock(int stockQuantity){
        int restStock = this.stockQuantity -=stockQuantity;
        if (restStock < 0){
            throw new NotEnoughStockException("재고가 부족합니다");
        }
        this.stockQuantity = restStock;
    }
}
