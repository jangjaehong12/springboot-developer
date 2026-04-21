package me.jhjang.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jhjang.springdeveloper.dao.Article;
import me.jhjang.springdeveloper.dto.AddArticleRequest;
import me.jhjang.springdeveloper.dto.UpdateArticleRequest;
import me.jhjang.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; //객체 -> json 문자열 변환

    @Autowired
    private BlogRepository blogRepository;

    @BeforeEach
    public void cleanUp() {
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception{
        //given
        final String url = "/api/articles";
        final String title = "테스트";
        final String content = "블로그 글 첫번째 입니다";
        final AddArticleRequest article = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(article);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)); //Http Request 보내는 것을 흉내냄

        //then
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }
    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        // given : 준비 단계 : 데이터를 하나 삽입
        blogRepository.save(
                Article.builder()
                        .title("제목 1")
                        .content("내용 1")
                        .build()
        );

        final String url = "/api/articles";

        // when : GET 방식으로 /api/articles 요청
        final ResultActions resultActions = mockMvc.perform(
                get(url).accept(MediaType.APPLICATION_JSON));

        // then : status OK이고, 읽어온 데이터와 삽입한 데이터가 동일하다.
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("내용 1"))
                .andExpect(jsonPath("$[0].title").value("제목 1"));
    }
    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findByArticle() throws Exception{
        //given (데이터 준비 : 블로그 글 하나 생성)
        final String url = "/api/articles/{id}";
        final String title = "블로그 글 제목";
        final String content = "블로그 내용";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        // when (실행: 위에서 생성된 블로그 글 조회)
        final ResultActions resultActions = mockMvc.perform(
                get(url, savedArticle.getId())
                        .accept(MediaType.APPLICATION_JSON)
        );
        //then (검증: status가 200이고 조회한 블로그 글 제목과 내용이 위에서 삽입한 그것과 동일한지 확인
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }
    @DisplayName("deleteArticle: 블로그 글 삭제 성공한다")
    @Test
    public void deleteArticle() throws Exception{
        //given
        final String url = "/api/articles/{id}";
        final String title = "4월 16일";
        final String content = "백엔드 프로그래밍2 수업";
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        //when
        mockMvc.perform(delete(url, savedArticle.getId())).andExpect(status().isOk());

        //then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }
    @DisplayName("updateArticle: 블로그 글 수정 성공")
    @Test
    public void updateArticle() throws  Exception{
        //given 레코드 생성 , 변경내용 작성
        final String url = "/api/articles/{id}";
        final String title = "JUnit 제목";
        final String content = "Junit 내용";
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());
        final String newTitle = "Junit에서 제목 변경";
        final String newContent = "Junit에서 내용 변경";
        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);
        //when /api/articles/생성된 레코드 id -> put 방식 요청
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        //then status code가 200, repository에서 변경된 내용 검증
        result.andExpect(status().isOk());
        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }
}
//      개인실습
//    @DisplayName("블로그 글 목록 조회")
//    @Test
//    public void findAllArticles() throws Exception {
//        // given
//        blogRepository.save(new Article("제목1", "내용1"));
//        blogRepository.save(new Article("제목2", "내용2"));
//
//        final String url = "/api/articles";
//
//        // when
//        ResultActions result = mockMvc.perform(get(url)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].title").value("제목1"))
//                .andExpect(jsonPath("$[0].content").value("내용1"))
//                .andExpect(jsonPath("$[1].title").value("제목2"))
//                .andExpect(jsonPath("$[1].content").value("내용2"));
//    }
