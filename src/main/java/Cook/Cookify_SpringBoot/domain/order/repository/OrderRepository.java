package Cook.Cookify_SpringBoot.domain.order.repository;

import Cook.Cookify_SpringBoot.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
