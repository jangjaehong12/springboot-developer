package me.jhjang.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.jhjang.springdeveloper.dao.Article;
import me.jhjang.springdeveloper.dto.AddArticleRequest;
import me.jhjang.springdeveloper.dto.ArticleResponse;
import me.jhjang.springdeveloper.dto.UpdateArticleRequest;
import me.jhjang.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest articleRequest) {
        Article article = blogService.save(articleRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<Article> articles = blogService.findAll();
        List<ArticleResponse> result = articles
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(result);


    }
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article article = blogService.update(id, request);
        return ResponseEntity.ok().body(article);
    }
}

