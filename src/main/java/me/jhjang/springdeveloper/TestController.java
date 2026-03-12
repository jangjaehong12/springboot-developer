package me.jhjang.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //@Controller + @ResponseBody ★시험출제
//모드 메서드가 데이터를 리턴한다.
//@Controller = 기본적으로 메서드 리턴하는 것이 HTML 파일이름이다.
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    //@ResponseBody
    public List<Member> getAllMembers() {
        return testService.getAllMembers(); //테스트 데이터를 수동으로 넣도록 만든다
    }
}
