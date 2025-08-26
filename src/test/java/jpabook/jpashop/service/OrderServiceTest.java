package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.hibernate.annotations.TenantId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 주문() {
        // given
        final Member member = createMember();

        int bookPrice = 10000;
        int orderCount = 2;
        final Book book = createBook(bookPrice, 10);

        // when
        final Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        final Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(bookPrice * orderCount, getOrder.getTotalPrice());
        assertEquals(8, book.getStockQuantity());
    }

    @Test
    public void 주문초과() {
        final Member member = createMember();
        int bookPrice = 10000;
        int orderCount = 11;
        final Book book = createBook(bookPrice, 10);

        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생되어야 한다.");
    }

    @Test
    public void 주문취소() {
        final Member member = createMember();
        int bookPrice = 10000;
        int bookQuantity = 10;
        final int orderCount = 2;
        final Book book = createBook(bookPrice, bookQuantity);
        final Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        final Order findOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, findOrder.getStatus());
        assertEquals(bookQuantity, book.getStockQuantity());
    }


    private Book createBook(int bookPrice, int bookQuantity) {
        final Book book = new Book();
        book.setStockQuantity(bookQuantity);
        book.setPrice(bookPrice);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        final Member member = new Member();
        member.setName("kim");
        em.persist(member);
        return member;
    }
}