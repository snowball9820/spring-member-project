package snowball.core.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import snowball.core.AppConfig;
import snowball.core.member.MemberService;
import snowball.core.member.MemberServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        System.out.println("memberService = "+memberService);
//        System.out.println("memberService.getClass = "+memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }
    //인터페이스로 조회
    @Test
    @DisplayName("이름없이 타입으로 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }
    //사실 구체적으로 적는 것은 좋지 않음 구현에 의존하는 것이기 때문에 좋지 않은 코드는 맞음
    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        MemberService memberService = ac.getBean("memberService",MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }
    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX(){
//        MemberService memberService = ac.getBean("xxxxx", MemberService.class);
        assertThrows(NoSuchBeanDefinitionException.class,
                ()->ac.getBean("xxxxx", MemberService.class)); //오른쪽 로직을 실행하면 왼쪽 예외가 터져야 함
    }
}

