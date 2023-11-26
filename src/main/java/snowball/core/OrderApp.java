package snowball.core;

import snowball.core.member.Grade;
import snowball.core.member.Member;
import snowball.core.member.MemberService;
import snowball.core.member.MemberServiceImpl;
import snowball.core.order.Order;
import snowball.core.order.OrderService;
import snowball.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        //AppConfig에서 필요한 것을 꺼내씀

        AppConfig appConfig=new AppConfig();
        MemberService memberService= appConfig.memberService();
        OrderService orderService= appConfig.orderService();
//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 20000);
        System.out.println("order = " + order);
        System.out.println("order.calculatePrice = "+order.calculaterPrice());

    }
}
