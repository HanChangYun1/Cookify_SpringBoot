package Cook.Cookify_SpringBoot.domain.order;

import Cook.Cookify_SpringBoot.domain.delivery.DeliveryStatus;
import Cook.Cookify_SpringBoot.domain.order_item.OrderItem;
import Cook.Cookify_SpringBoot.global.Entity.BaseEntity;
import Cook.Cookify_SpringBoot.global.Entity.BaseTimeEntity;
import Cook.Cookify_SpringBoot.domain.delivery.Delivery;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_id")
    private GoogleMember member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //생성 메서드//
    public static Order createOrder(GoogleMember member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem: orderItems){
            order.addOrderItems(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        return order;
    }


    //연관관계 메서드//
    public void confirmMember(GoogleMember member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItems(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void confirmDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    //주문 취소//
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems){
            orderItem.cancle();
        }
    }

    //조회 로직//
    //전체 주문 가격 조회//
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
