package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 관계 매핑
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name; // 아이템 이름
    private int price; // 아이템 가격
    private int stockQuantity; // 아이템 재고

    @ManyToMany(mappedBy = "items") // Category와 다대다 관계
    private List<Category> categories = new ArrayList<>(); // 아이템이 속한 카테고리들

    /**
     * 재고 증가
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity; // 재고 증가
    }

    /**
     * 재고 감소
     * @param quantity
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity; // 재고 감소
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다. 현재 재고: " + this.stockQuantity);
        }
        this.stockQuantity = restStock; // 재고 업데이트
    }

}
