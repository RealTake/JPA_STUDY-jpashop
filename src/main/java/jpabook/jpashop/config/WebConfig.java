package jpabook.jpashop.config;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public Hibernate5JakartaModule hibernate5JakartaModule() {
        Hibernate5JakartaModule module = new Hibernate5JakartaModule(); // 기본 설정: 초기화 되지 않은 엔티티의 프록시객체는 null로 반환
//        module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true); // API 응답시 지연로딩을 강제로 초기화
        return module;
    }
}
