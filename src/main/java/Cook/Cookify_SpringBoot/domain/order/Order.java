package Cook.Cookify_SpringBoot.domain.order;

import Cook.Cookify_SpringBoot.domain.BaseTimeEntity;
import Cook.Cookify_SpringBoot.domain.delivery.Delivery;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "orders")
@NoArgsConstructor
@Entity
public class Order extends BaseTimeEntity {

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

}
