package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 'order'는 SQL 예약어이므로 다른 이름으로 지정
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성 매서드 사용 유도
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    // 주문 시간

    @Enumerated(EnumType.STRING) // Enum 타입을 문자열로 저장
    private OrderStatus status;         // 주문 상태 (ORDER, CANCEL)

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this); // 양방향 연관관계 설정
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     * 주문 --생성 메서드--
     * */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        final Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        if (getDelivery().getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        setStatus(OrderStatus.CANCEL);
        orderItems.stream()
                .forEach(OrderItem::cancel);
    }

    /**
     * 전체 주문 가격 조회 메서드
     */
    public int getTotalPrice() {
        return getOrderItems().stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
