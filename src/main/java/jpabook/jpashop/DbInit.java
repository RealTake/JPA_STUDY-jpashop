package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

//@Component
@RequiredArgsConstructor
public class DbInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

//    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager entityManager;

        public Member createMember(String name, String city, String street, String zipcode) {
            final Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));

            return member;
        }

        public Book createBook(String name, int price, int stockQuantity) {
            final Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);

            return book;
        }

        public Delivery createDelivery(Member member) {
             return new Delivery(member.getAddress(), DeliveryStatus.READY);
        }

        public void dbInit1() {
            final Member member = createMember("userA", "서울", "1", "11111");
            entityManager.persist(member);

            final Book book1 = createBook("JPA1 BOOK", 10000, 100);
            entityManager.persist(book1);

            final Book book2 = createBook("JPA2 BOOK", 20000, 100);
            entityManager.persist(book2);

            final OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            final OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            final Delivery delivery = createDelivery(member);
            final Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            entityManager.persist(order);
        }

        public void dbInit2() {
            final Member member = createMember("userB", "부산", "1", "11111");
            entityManager.persist(member);

            final Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            entityManager.persist(book1);

            final Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            entityManager.persist(book2);

            final OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            final OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            final Delivery delivery = createDelivery(member);
            final Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            entityManager.persist(order);
        }
    }
}
