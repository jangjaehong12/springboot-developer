package me.jhjang.springdeveloper.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.jhjang.springdeveloper.dao.Article;
import me.jhjang.springdeveloper.dto.ArticleResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) {
        List<ArticleResponse> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArticleResponse a = new ArticleResponse(new Article("제목" + i,"번째 내용"));
            list.add(a);
        }
        model.addAttribute("articles", list);
        model.addAttribute("name","홍길동");
        model.addAttribute("error","검색 오류발생");

        Person p=new Person(1L, "고길동", 18, List.of("운동","독서","영화","음악","등산"));
        model.addAttribute("person",p);
        model.addAttribute("today",LocalDate.now());
        return "example"; // src/main/resources/templates/example.html
    }
}
@Getter
@AllArgsConstructor
class Person {
    private Long id;
    private String name;
    private int age;
    private List<String> hobbies;
}
