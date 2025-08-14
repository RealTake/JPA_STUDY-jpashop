package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A") // Item의 Dtype을 A로 설정
@Getter
@Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
