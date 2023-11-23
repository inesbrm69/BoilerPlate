package com.ynov.boilerplate.repository;

import com.ynov.boilerplate.entity.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository underTest;
    @Test
    void itShouldCheckWhenArticleExistsByIdAndName() {
        //given
        Article article = new Article(
                5,
                "Lunettes",
                500
        );
        underTest.save(article);

        //when
        boolean expected = underTest.doesArticleExist(5, "Lunettes");

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenArticleNameDoesNotExists() {
        //given
        String name = "Cahier";

        //when
        boolean expected = underTest.doesArticleExistByName(name);

        //then
        assertThat(expected).isFalse();
    }
}