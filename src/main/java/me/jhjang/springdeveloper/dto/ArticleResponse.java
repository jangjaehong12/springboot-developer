package me.jhjang.springdeveloper.dto;

import lombok.Getter;
import me.jhjang.springdeveloper.dao.Article;

@Getter
public class ArticleResponse {
    private  final Long id;
    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
