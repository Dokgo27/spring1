package hello.hellospring.repository;

// domain/Member 클래스 import
// 회원 정보를 가져온다.
import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional; // 가져오는 값에 대해 없으면 NULL을 그대로 반환하는 것이 아니라 Optional로 감싸서 반환

// 인터페이스를 구축한다. {!} 인터페이스는 추상메서드로 이루어진다.
public interface MemberRepository {
    // 회원을 저장한다.
    Member save(Member member);
    // 회원의 id를 찾는다.
    Optional<Member> findById(Long id);
    // 회원의 이름을 찾는다.
    Optional<Member> findByName(String name);
    // 저장된 모든 회원을 반환한다.
    List<Member> findAll();
}
