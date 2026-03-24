package me.jhjang.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest2 {
    @Autowired
    private MockMvc movckMvc;

    @Test
    @DisplayName("Get / Test2 요청시 Hello world 반환")
    void getTestAPI() throws Exception{
        //Given : 없음

        //When & Then
        movckMvc.perform(get("/test2"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }
}
