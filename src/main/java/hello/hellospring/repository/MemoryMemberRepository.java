package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository 컴포넌트 스캔: 스프링 컨테이너가 Repository(저장소)라고 인식한다.
public class MemoryMemberRepository implements MemberRepository{ // 인터페이스 MemberRepository 추상 메소드 구현하는 클래스 MemoryMemberRepository

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 0부터 key값을 자동으로 +1 해주는 sequence

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // 먼저 회원이 저장되면 key 값을 +1 한 값을 member.id에 저장
        store.put(member.getId(), member); // HashMap store 자료에 수정된 member의 정보를 삽입한다.
        return member;
    }

    // 매개변수로 들어온 id에 대해 store 해쉬맵의 키값과 비교하여 member 반환
    // null값이 될 수 있는 store.get(id)을 리턴하면 안된다.
    // Optional.ofNullable()을 써서 Optional로 감싸서 내보낸다.
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 해쉬맵에 들어있는 멤버정보를 싹 비우는 메소드
    // 테스트 케이스에서 쓰인다
    public void clearStore(){
        store.clear();
    }
}

/*
테스트 케이스 작성 요령
 */
