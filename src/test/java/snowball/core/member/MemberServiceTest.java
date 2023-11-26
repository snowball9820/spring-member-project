package snowball.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snowball.core.AppConfig;

public class MemberServiceTest {
    MemberService memberService;

    //테스트를 실행하기 전에
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig=new AppConfig();
        memberService= appConfig.memberService();
    }
//    MemberService memberService=new MemberServiceImpl();
    @Test
    void join(){
        //given
        Member member=new Member(1L,"memberA",Grade.VIP);
        //when
        memberService.join(member);
        Member findMember=memberService.findMember(1L);
        //then member와 findMember가 같나? isEqualTo
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
