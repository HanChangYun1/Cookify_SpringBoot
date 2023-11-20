package Cook.Cookify_SpringBoot.domain.item.repository;

import Cook.Cookify_SpringBoot.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
