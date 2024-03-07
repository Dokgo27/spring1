package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import org.springframework.ui.Model;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
/*
내가 이제까지 만들었던 로직들(MemberService, MemoryMemberRepository..)을 이제 화면에 붙일라고 한다. -> Controller와 ViewTemplate가 이제 필요하다.
MemberController는 MemberService를 통해서 Member의 정보를 조회하거나 회원가입이 가능할 수 있게 해야한다.(의존관계)

스프링 빈을 등록하는 방법 2가지(엄청 중요하다.)
    1. 컴포넌트 스캔: @Component이 있으면 자동으로 스프링 빈으로 등록된다. @Service, @Controller.. 등등 모두가 @Component를 이미 가지고 있다.
    2. 하나하나 직접 코드로 스프링 빈으로 등록한다. @Configuration, @Bean
        {!} 같은 패키지의 하위 파일과 패키지에만 적용된다. 즉 기본적으로는...hello.hellospring의 하위에서만 컴포넌트 스캔한다.(특정한 설정을 해주면 가능하다고는 한다)
        {!} 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 싱글톤(유일하게 하나만)으로 등록해서 공유한다. 즉, 같은 스프링 빈은 같은 인스턴스이다.(특정한 설정을 해주면 복수로 설정 가능하다고는 한다)

방법 1. 컴포넌트 스캔 (정형화된 서비스인 컨트롤러, 서비스, 리포지토리와 같은 코드에 사용)
    1. 스프링 창이 뜰 때 스프링 컨테어너라는 통이 생긴다. 그 후 @이 붙은 모든 객체(스프링 빈)들을 컨테이너에 넣어두어 관리한다.
    2. 순수 자바 클래스면 스프링이 식별하지 못해서 의존성 주입이 되지 않는다.
       @(어노테이션)이 붙어야 스프링 빈으로 등록된다(@Controller, @Service, @Repository...)
    3. Controller에 공용으로 사용할 객체는 Controller 생성자에 @Autowired를 해서 매개변수로 받고 Controller에 매칭을 시켜줘야한다. ---> 의존성 주입(Dependency Injection)
       또한 MemberService의 생성자에도 @Autowired를 붙여줘야 한다.

방법 2. 하나하나 직접 코드로 스프링 빈으로 등록한다.(정형화되지 않거나, 상황에 따라 구현 클래스를 변경해야 할 때) ** 데이터 저장소(DB)가 아직 정해지지 않았다는 전제등등..
    1. 최상위 패키지에 새로운 클래스 파일을 생성한다(SpringConfig 용)
    2. @Configuration을 통해 직접 코드로 빈으로 등록하는 것을 명시한다.
    3. 스프링 빈으로 등록하고자 하는 객체를 @Bean을 통해 의존성 주입을 한다. MemberController -> MemberService -> MemberRepository -> MemoryMemberRepository 순

DI(Dependency Injection) 방법 3가지
1. 생성자 주입 --> 이게 제일 추천하는 방식이라고 합니다.
2. 필드에다가 @Autowired를 바로 때려박아서 주입 --> 별로 안좋음(바꿀 수 있는 수단이 없다.)
3. Setter 주입 --> 누군가가 MemberController를 호출했을 때 Setter가 Public으로 되어있어야 함. 잘못바꾸면 문제 생긴다고 한다.
 */

@Controller
public class MemberController {
    /*
    private final MemberService memberService = new MemberService();
    위의 줄같이 new연산자를 통해서 객체를 생성하게 되면 다른 컨트롤러들이 접근을 할 수 있게 된다.
     */
    private final MemberService memberService;

    /*
    Setter 주입
    @Autowired
    public void setMemberService(MemberService memberService){
        this.memberService = memberService;
    }
     */

    /*
    컴포넌트 스캔이던 직접 코드로 주입하는 거랑 처음 컨트롤러에서는 써야한다.
    생성자 주입
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new") // 회원가입 링크 클릭해서 오면
    public String createForm() {
        return "members/createMemberForm";
    } // createMemberForm.html을 반환해라(멤버 가입 페이지)

    @PostMapping("/members/new")
    public String create(MemberForm form) { // 멤버 폼이 매개변수로 들어옵니다(id, name)
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member); // 만들어두었던 멤버 서비스에 회원가입(join) 메서드를 통해 회원을 저장합니다.
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
