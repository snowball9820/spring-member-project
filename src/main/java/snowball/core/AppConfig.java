package snowball.core;

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

public class AppConfig {

//    public MemberService memberService() {
//        return new MemberServiceImpl(new MemoryMemberRepository()); //생성자 주입 //내가 만든 MemeberServiceImpl은 MemoryMemeberRepository를 쓸거야 주입!
//    }
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }
    private MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

//    public OrderService orderService() {
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy()); //생성자 주입
//
//    }
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(),discountPolicy());

    }
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
