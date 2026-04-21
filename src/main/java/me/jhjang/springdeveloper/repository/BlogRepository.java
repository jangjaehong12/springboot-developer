package me.jhjang.springdeveloper.repository;

import me.jhjang.springdeveloper.dao.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article,Long> {

}
