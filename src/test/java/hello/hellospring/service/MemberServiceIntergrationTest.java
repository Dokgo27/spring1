package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 테스트 케이스에서 마구마구 DB에 접근하다가 테스트가 끝나면 DB를 롤백시켜주는 트렌젝션
class MemberServiceIntergrationTest {
    
    // 테스트 코드는 그냥 필드 인젝션
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    public void 회원가입() {
        // given - 무언가가 주어지면
        Member member = new Member();
        member.setName("spring");

        // when - 이거를 실행했을 때
        Long saveId = memberService.join(member);

        // then - 이거를 실행하여야 한다.
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        // given - 무언가가 주어지면
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when - 이거를 실행했을 때
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        // 1. org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 2. org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> memberService.join(member2));
        // 3. org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> memberService.join(member2));
        /* 4.
        memberService.join(member1);
        try{
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */
        // then - 이거를 실행하여야 한다.
    }
}