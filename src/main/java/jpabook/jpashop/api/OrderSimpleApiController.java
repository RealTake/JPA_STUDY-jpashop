package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;

    /**
     * 양방항 매핑 필드들이 Json으로 변환 될떄, 서로가 서로를 다시 접근하게 되는 오류가 발생하게 된다.
     * -> @JsonIgnore 같은 필드를 사용하여 일부 문제를 피할 수 있지만 매우 번거롭다.
     * Order 엔티티에 Lazy로 설정 되어 있는 필드들 중 초기화 되지 않은 필드프록시 객체가 대신 들어가 있어, 이를 Json에 활용할 수 없기 때문에 또다른 오류 발생
     * -> jackson-datatype-hibernate5 모듈 설정을 통해 초기화 되지 않은 프록시 객체는 노출 안하도록 설정 한다.
     * @return
     */
    @GetMapping("/v1/simple-orders")
    public List<Order> ordersV1() {
        final List<Order> findOrders = orderService.findOrders(new OrderSearch());

        return findOrders;
    }
}
