package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
//@Service 컴포넌트 스캔: 스프링이 실행될 때 MemberService를 컨테이너에 등록하겠다.
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired 컴포넌트 스캔
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    회원 가입
    */
    public Long join(Member member) {

        /*
        // 직접 메소드에 시간 적용
        long start = System.currentTimeMillis();
        
        try{
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }
         */
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    public void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    /*
    전체 회원 조회
     */
    public List<Member> findMembers(){
        /*
        long start = System.currentTimeMillis();
        try{
            return memberRepository.findAll();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
         */
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /*
        public Long join(Member member) {
            //같은 이름이 있는 중복 회원이 있으면 안된다.
            // memberRepository.findByName(member.getName());

            ------
            Optional<Member> result = memberRepository.findByName(member.getName()); // 매개변수로 받은 멤버가 멤버해쉬맵에 있는 회원의 이름과 동일한 결과값이 있다면
            result.ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다."); // 오류 던지기
            });
            ------

            memberRepository.findByName(member.getName())
                    .ifPresent(m -> {
                        throw new IllegalStateException("이미 존재하는 회원입니다."); // 오류 던지기
                    });
            memberRepository.save(member); // 먼저 회원에대한 정보를 저장한다.
            return member.getId();
        }
    */
}
