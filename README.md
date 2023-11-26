# spring_memberProject
![image](https://github.com/snowball9820/spring_memberProject/assets/124758100/aab36e6a-3477-44a6-968e-b894abf4adcd)

### 순서

회원 도메인 설계, 개발  
회원 도메인 실행과 테스트  
주문과 할인 도메인 설계, 개발  
주문과 할인 도메인 실행과 테스트 

### 회원
회원을 가입하고 조회할 수 있음  
회원은 일반과 VIP 2가지 등급이 존재  
회원 데이터 자체 DB 구축 가능, 외부 시스템과 연동 할 수 있음(그러나 미확정)

### 주문과 할인 정책
회원은 상품을 주문 가능  
회원 등급에 따라 할인 정책 적용  
할인 정책은 모든 VIP는 1000원 할인 고정 금액 할인 적용 요구
(추후 변경 가능성 있음)  
할인 정책은 변경 가능성이 높고 회사의 기본 할인 정책 미정
오픈 직전까지 미확정, 최악의 경우 할인 적용 X

:현재 결정 되지 않은 부분이 있음, 객체 지향 설계 방법 사용

-> SOLID

SRP:단일 책임 원칙(single responsibility principle)

OCP:개방-폐쇄 원칙(Open/closed principle)

LSP:리스코프 치환 원칙(Liskov substitution principle)

ISP:인터페이스 분리 원칙(Interface segregation principle)

DIP:의존관계 역전 원칙(Dependency inversion principle)


:인터페이스 만들고 구현체를 갈아 끼울 수 있도록 설계 

### 회원 도메인 설계

![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)

### 회원 도메인 설계 문제점
설계상 문제점은 무엇인가?  
다른 저장소로 변경할 때 OCP 원칙을 잘 준수할까?  
DIP를 잘 지키고 있나?

=>의존관계가 인터페이스 뿐만 아니라 구현까지 모두 의존...  
MemberServiceImpl은 MemberRepository와 MemoryMemberRepository 추상화, 구현체에도 의존...DIP위반  

---
### 주문과 할인 도메인 설계

회원은 상품 주문 가능  
회원 등급에 따라 할인 정책 적용  
할인 정책은 모든 VIP는 1000원 할인(고정 금액 할인 적용-추후 변경 가능성 O)  
최악의 경우 할인 적용 X  

1.주문 생성: 클라이언트는 주문 서비스에 주문 생성 요청  
2.회원 조회: 할인을 위해서는 회원 등급 필요, 주문 서비스는 회원 저장소에서 회원 조회  
3.할인 적용: 주문 서비스는 회원 등급에 따른 할인 여부를 할인 정책에 위임  
4.주문 결과 반환: 주문 서비스는 할인 결과를 포함한 주문 결과 반환  

*주문 데이터 DB 저장X, 주문 결과 단순 반환만함 

![img_4.png](img_4.png)
![img_5.png](img_5.png)
![img_6.png](img_6.png)
![img_7.png](img_7.png)

회원을 메모리에서 조회, 정액 할인 정책(고정 금액)을 지원해도 주문 서비스 변경하지 않아도 됨  
역할들의 협력 관계를 그대로 재사용 가능  
:메모리에서 DB로 바뀌거나 정액 할인 정책이 정률 할인 정책이 되어도 그대로 재사용 가능  
(객체지향의 사실과 오해를 읽어보자...)

---

### 새로운 할인 정책 개발

요구사항-주문 금액당 할인하는 정률 % 할인으로 변경  
예를 들어서 기존 정책은 VIP가 얼마를 주문하던지 항상 1000원 할인  
새 정책은 10%로 지정하면 주문 금액에 따라 할인 가격이 달라져야 함

---

### 새로운 할인 정책 적용과 문제점
![img_12.png](img_12.png)
->할인 정책을 변경하려면 클라이언트인 OrderServiceImpl를 고쳐야 함  
  
문제점  
1.역할과 구현을 충실하게 분리했나? O  
2.다형성 활용, 인터페이스와 구현 객체 분리했나? O  
3.OCP, DIP 등 객체지향 설계 원칙을 준수했나? 그렇게 보이나 사실은 X  
4.DIP 주문서비스 클라이언트('OrderServiceIml')는 DiscountPolicy 인터페이스에 의존하고 DIP를 지킨 것 처럼 보임  
->클래스 의존관계를 보면 추상(인터페이스)뿐만 아니라 구체(구현)클래스에도 의존  
- 추상(인터페이스)의존 'DiscountPolicy'  
- 구체(구현)클래스 'FixDiscountPolicy', 'RateDiscountPolicy'  

5.OCP 변경하지 않고 확장 가능?  
->지금 코드는 기능을 확장해서 변경하면 클라이언트 코드에 영향을 줌->OCP 위반  
  
DiscountPolicy 인터페이스에만 의존한다고 생각함  
![img_9.png](img_9.png)  
OrderServiceImpl는 DiscountPolicy 인터페이스 뿐만 아니라 FixDiscountPolicy인 구현체 클래스에도 함께 의존  
  
->FixDiscountPolicy를 RateDiscountPolicy로 변경하는 순간 OrderServiceImpl의 소스 코드도 함께 변경해야 함  
##### OCP 위반!

### 문제 해결 방법    
클라이언트 코드 OrderServiceImpl는 DiscountPolicy의 인터페이스 뿐만 아니라 구현 클래스에도 의존  
구현 클래스를 변경할 때 클라이언트 코드도 함께 변경해야 하는 상황  
->추상(인터페이스)에만 의존하도록 하자  
##### 인터페이스에만 의존하도록 설계  
![img_11.png](img_11.png)  
이렇게 되면 구현체가 없음  
![img_10.png](img_10.png)
##### NullPointerException 발생...  
->DIP를 지키면 다른 문제가 발생  
->OrderServiceIml에 DiscountPolicy 대신 구현 객체 생성과 주입 필요  

---  

### 관심사 분리  
자신의 역할을 수행하는 것에만 집중
어떠한 것이 오더라도 똑같이 동작할 수 있어야 함
역할에 맞도록 지정하는 책임을 담당하는 별도의 기획자가 나와야 함
  
### AppConfig  
애플리케이션의 전체 동작 방식을 구성, 설정하기  
구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스 생성    

AppConfig는 애플리케이션의 실제 동작이 필요한 구현 객체 모두 생성  
- MemberServiceIml  
- MemoryMemberRepository  
- OrderServiceIml
- FixDiscountPolicy    

AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 '생성자를 통해 주입(연결)'  
- MemberServiceIml->MemoryMemberRepository
- OrderServiceIml->MemoryMemberRepository, FixDiscountPolicy  
   
```public class MemberServiceImpl implements MemberService{

    //오로지 MemberRepository 인터페이스에만 의존하게 됨 -> 추상화에만 의존 
    private final MemberRepository memberRepository;

    //생성자를 통해 MemberRepository에 어떤 구현체가 들어가는지 선택
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }//생성자 만듦

    @Override
    public void join(Member member) {memberRepository.save(member);}//다형성에 의해서 인터페이스가 아니라 MemoryMemberRepository에 있는 save가 호출됨

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
```  

##### 설계 변경  
MemberSerImpl은 MemoryMemberRepository를 의존하지 않음  
->단지 MemeberRepository 인터페이스에 의존  
* 생성자를 통해 어떤 구현객체가 주입될지 알 수 없음  
* 생성자를 통해 어떤 구현 객체를 주입할지는 오직 AppConfig에서 결정
* 의존관계에 대한 고민은 AppConfig에 맡기고 실행에만 집중하게 됨  

##### 클래스 설명  
MemeberService 인터페이스를 구현하는 것은 MemberServiceImpl임  
MemberServiceImpl는 MemberRepository 인터페이스를 의존  
여기서 AppConfig를 만들면  
MemeberServiceImpl와 MemoryMemeberRepository 생성을 AppConfig가 담당함  
객체와 생성과 연결을 AppConfig가 담당  
->DIP 완성->관심사 분리->객체를 생성하고 연결하는 역할과 실행하는 역할 분리       
  
```
public class OrderServiceImpl implements OrderService{
    //아래 두개 필요 MemoryMemberRepository와 FixDiscountPolicy 구현체가 있어야지
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    //final로 되어있으면 생성자를 통해 할당이 되어야 함
    //DIP를 지키고 있음
    //인터페이스에만 의존

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //private final DiscountPolicy discountPolicy=new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy=new RateDiscountPolicy();
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        //일단 멤버 찾고
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        //설계가 잘된거임...할인에 대한건 discountPolicy 니가 알아서 해 =>단일 책임 원칙 준수

        //orderServiceImpl 역할 끝
        //조회후 할인 정책에다가 회원을 그냥 넘김
        //Grade만 넘길지 회원을 넘길지 고민하면 됨
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
```  
OrderServiceImpl도 설계 변경  
FixDiscountPolicy를 의존하지 않음  
생성자를 통해 어떤 구현 객체가 주입될지 알 수 없음->AppConfig가 결정  
이제 실행에만 집중    
  
---  
  
##### 마무리  
* AppConfig를 통해 관심사를 확실하게 분리  
* MemberServiceImpl, OrderServiceImpl는 기능을 실행하는 책임만 지면 됨  
  
### AppConfig refactoring 
현재 AppConfig의 문제점
* AppConfig에 중복 존재  
* 역할에 따른 구현이 잘 보이지 않음    
  
##### 현재 AppConfig
  
```
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); //생성자 주입 //내가 만든 MemeberServiceImpl은 MemoryMemeberRepository를 쓸거야 주입!
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy()); //생성자 주입

    }
}

```    
원하는 구조
![img_13.png](img_13.png)  
  
```
public class AppConfig {

    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }
    private MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(),discountPolicy()); 

    }
    public DiscountPolicy discountPolicy(){
        return new FixDiscountPolicy();
    }
}
```  
  
* new MemoryMemeberRepository()이 부분이 중복 제거
* MemoryMemberRepository를 다른 구현체로 변경할 때 한 부분만 변경하면 됨  
* AppConfig를 보면 역할과 구현 클래스가 한 눈에 들어옴


  

  



##### *새로운 단축키...
psvm+enter

soutv 

Alt+insert -> generate 

Ctrl+A -> 전체 선택

Shift+Ctrl+Alt+L ->코드 정렬

F2 -> 오류 발생 지점으로 이동

Ctrl+Shift+T -> Test 생성

Assertions -> static method  demand static import하면 assertThat으로만 사용 가능

Ctrl+Shift6 -> 변수명 변경 일괄 적용  

Ctrl+E -> 과거 히스토리 목록    
  
Ctrl+Alt+M -> 리팩터링
