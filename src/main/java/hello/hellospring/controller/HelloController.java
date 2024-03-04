package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 1. 웹 브라우저가 localhost:8080/hello를 던짐
 2. 내장 톰켓 서버로 감
 3. 톰켓 서버가 스프링 컨테이너로 가서 helloController를 찾음(return:hello, model(data:hello))
 4. viewResolver(템플릿 엔진 처리)가 화면을 찾아서 처리한다. --> hello.html(변환 후)던짐
 */
@Controller
public class HelloController {

    // localhost:8080/hello
    // resources:static/hello.html
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello");
        return "hello";
    }

    // localhost:8080/hello-mvc
    // resources:static/hello-static.html
    // 인자값 즉시보기 Ctrl + p
    // required = true가 기본값
    // 예시 : http://localhost:8080/hello-mvc?name=spring!!!
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = true) String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    /*
    @ResponseBody를 사용 --> return 값의 타입에 따라 HttpMessageConverter가 판단 (ViewResolver 대신)
    1. 문자일 때 --> StringHttpMessageConverter
    2. 객체일 때 --> MappingJackson2HttpMessageConverter (Json 방식으로 내보냄) {!}MappingJackson2 --> 객체를 Json으로 바꿔주는 라이브러리
    {!} 요즘은 거의 Json 쓴다.
     */
    @GetMapping("hello-string")
    @ResponseBody // http 프로토콜 body에 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name){
        return "hello " + name; // 문자일때는 http 응답에 바로 넣음
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 객체일때는 Json 형식으로 내보내짐  // json = {"key" : "value"}

    }

    // 접근자 설정자 단축키 Art + insert
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
