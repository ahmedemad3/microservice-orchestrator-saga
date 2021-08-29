package eg.com.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eg.com.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
