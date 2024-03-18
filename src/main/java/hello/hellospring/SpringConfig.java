package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

//방법 2. 하나하나 직접 코드로 스프링 빈으로 등록한다.
@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }
     */

    /*
    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    */

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

    /*
    스프링 빈 직접 생성 (AOP)
    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
     */


//    @Bean
//    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        return new jdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//   }
}
