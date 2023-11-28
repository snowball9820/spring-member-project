package snowball.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import snowball.core.discount.DiscountPolicy;
import snowball.core.discount.FixDiscountPolicy;
import snowball.core.discount.RateDiscountPolicy;
import snowball.core.member.MemberRepository;
import snowball.core.member.MemberService;
import snowball.core.member.MemberServiceImpl;
import snowball.core.member.MemoryMemberRepository;
import snowball.core.order.Order;
import snowball.core.order.OrderService;
import snowball.core.order.OrderServiceImpl;

//AppConfig는 의존관계 주입을 대신 해준다 DI 컨테이너 역할
@Configuration
public class AppConfig {

    //    public MemberService memberService() {
//        return new MemberServiceImpl(new MemoryMemberRepository()); //생성자 주입 //내가 만든 MemeberServiceImpl은 MemoryMemeberRepository를 쓸거야 주입!
//    }
    //@Bean memberService->new MemoryMemberRepository() 호출
    //@Bean orderService->new MemoryMemberREpository() 호출 2번째 호출 ->싱글톤이 깨질까?

    //호출 순서 가정
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.memberRepository
    //call AppConfig.orderService
    //call AppConfig.memberRepository
    //결과적으로 memberRepository가 3번 호출되어야 함
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    //    public OrderService orderService() {
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy()); //생성자 주입
//
//    }
    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());

    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
