package snowball.core.member;

public interface MemberService {
    //회원 가입, 조회
    void join(Member member);

    Member findMember(Long memberId);
}
