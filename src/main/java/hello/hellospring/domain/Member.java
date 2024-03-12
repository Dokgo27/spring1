package hello.hellospring.domain;

import jakarta.persistence.*;

// domain/Member - 회원에 대한 정보를 나타냄
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - DB가 쿼리 없이 알아서 생성해주는거
    private Long id;

    @Column(name = "username")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
