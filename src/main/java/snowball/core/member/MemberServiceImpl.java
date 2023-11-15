package snowball.core.member;

public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository=new MemoryMemberRepository();
    @Override
    public void join(Member member) {
        memberRepository.save(member);

    }//다형성에 의해서 인터페이스가 아니라 MemoryMemberRepository에 있는 save가 호출됨

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
