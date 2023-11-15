package snowball.core.discount;

import snowball.core.member.Member;

public interface DiscountPolicy {

    //F2를 누르면 오류난 곳으로 이동

    /**
     *
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
