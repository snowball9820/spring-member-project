package snowball.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac= new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A 사용자 10000원 주문
        int userAPrice=statefulService1.order("uerA",10000);
        //ThreadA: A 사용자 10000원 주문 중간에 들어옴
        int userBPrice=statefulService2.order("uerB",20000);

        //ThreadA: A 사용자 주문 금액 조회
        //기대한건 10000원인데 20000원 나옴
//        int price= statefulService1.getPrice();
        System.out.println("price = "+userAPrice);

//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

//        statefulService는 같은 객체인데 중간에 다른 값이 들어오면 바뀜..

    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}
