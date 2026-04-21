package me.jhjang.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jhjang.springdeveloper.dao.Article;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {
    private String title;
    private String content;
    public Article toEntity() {
//        return new Article(title, content);
        return Article.builder().title(title).content(content).build();
    }
    // NoArgsConstructor
    // new AddArticleRequest("제목","내용");
    // new AddArticleRequest();
}
