package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order; // 주문 정보

    @Embedded
    private Address address; // 배송지 정보

    @Enumerated(EnumType.STRING) // Enum 타입을 문자열로 저장
    private DeliveryStatus status; // 배송 상태 (READY, COMP) - READY: 준비, COMP: 완료

    /**
     * 배송정보 생성 메서드
     */
    public static Delivery createDelivery(Address address) {
        final Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setStatus(DeliveryStatus.READY);

        return delivery;
    }

    public void tset() {

    }
}
