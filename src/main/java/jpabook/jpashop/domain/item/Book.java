package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") // Item의 Dtype을 B로 설정
@Getter @Setter
public class Book extends Item{

    private String author; // 저자
    private String isbn; // ISBN 번호
}
