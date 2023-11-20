package snowball.core.member;

public class MemberServiceImpl implements MemberService{

    //오로지 MemberRepository 인터페이스에만 의존하게 됨
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {memberRepository.save(member);}//다형성에 의해서 인터페이스가 아니라 MemoryMemberRepository에 있는 save가 호출됨

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
