package Cook.Cookify_SpringBoot.domain.order.service;

import Cook.Cookify_SpringBoot.domain.item.repository.ItemRepository;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.order.Order;
import Cook.Cookify_SpringBoot.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final GoogleMemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void basket(){

    }

    @Transactional
    public void deleteBasket(){

    }

    @Transactional
    public void order(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        order.order();
    }

    @Transactional
    public void cancelOrder(Long orderId){

    }

//    public List<Order> findMemberOrders(){
//
//    }
}
