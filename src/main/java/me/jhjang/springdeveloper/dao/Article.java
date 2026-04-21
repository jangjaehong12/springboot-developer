package me.jhjang.springdeveloper.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Article {

       /* id (bigint, not null , primary key)
          title (varchar(255), not null)
          content (varchar(255), not null)
       */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
