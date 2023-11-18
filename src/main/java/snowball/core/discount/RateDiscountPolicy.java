package snowball.core.discount;

import snowball.core.member.Grade;
import snowball.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;

        } else {
            return 0;
        }

    }
}
