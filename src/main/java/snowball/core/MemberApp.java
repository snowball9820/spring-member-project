package snowball.core;

import snowball.core.member.Grade;
import snowball.core.member.Member;
import snowball.core.member.MemberService;
import snowball.core.member.MemberServiceImpl;

public class MemberApp {

    //psvm+enter
    //test 중
    //member와 findMemeber가 같으면 제대로 된거임
    public static void main(String[] args) {

        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        //soutv
        System.out.println("new member = " + member.getName());
        System.out.println("find Member=" + findMember.getName());


    }
}
