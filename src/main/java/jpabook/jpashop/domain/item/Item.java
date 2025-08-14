package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
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

}
