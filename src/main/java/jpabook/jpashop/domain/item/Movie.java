package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M") // Item의 Dtype을 M으로 설정
@Getter
@Setter
public class Movie extends Item {

    private String director; // 감독
    private String actor; // 배우
}
