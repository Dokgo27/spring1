package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
1. lcoalhost:8080/hello-static.html
2. 톰켓 서버로 감
3. 스프링 컨테이너에는 hello-static 관련 컨트롤러가 없음
4. resources:static/hello-static.html을 찾음
5. hello-static.html을 보냄
 */
@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);
	}
}
