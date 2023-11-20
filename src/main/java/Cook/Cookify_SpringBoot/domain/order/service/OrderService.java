package Cook.Cookify_SpringBoot.domain.order.service;

import Cook.Cookify_SpringBoot.domain.order.Order;

import java.util.List;

public interface OrderService {

    void basket();

    void deleteBasket();

    void order(Long orderId);

    void cancelOrder(Long orderId);

//    List<Order> findMemberOrders();

}
