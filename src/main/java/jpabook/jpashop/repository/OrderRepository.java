package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    /**
     * 주문 저장
     * @param order
     */
    public void save(Order order) {
        em.persist(order);
    }

    /**
     * 주문 조회
     * @param id
     * @return
     */
    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    /**
     * 주문 전체 조회
     * TODO: 구현 나중에
     */
    public List<Order> findAll(OrderSearch search) {
        return null;
    }

}
