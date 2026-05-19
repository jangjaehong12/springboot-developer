package me.jhjang.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.jhjang.springdeveloper.dao.Article;
import me.jhjang.springdeveloper.dto.ArticleResponse;
import me.jhjang.springdeveloper.dto.ArticleViewResponse;
import me.jhjang.springdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    // 게시글 목록 뷰
    @GetMapping("/articles")
    public String getArticles(Model model) {

        List<ArticleResponse> articles =
                blogService.findAll()
                        .stream()
                        .map(ArticleResponse::new)
                        .toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article",new ArticleViewResponse(article));
        return "articles";
    }
    @GetMapping("/new-article") // http://localhost:8080/new-article?id=1
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            // 수정 폼 페이지 요청하는 경우
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));

        } else { // 등록 폼 페이지 요청하는 경우
            Article article = new Article();
            model.addAttribute("article", new ArticleViewResponse());
        }

        return "newArticle";
    }
}