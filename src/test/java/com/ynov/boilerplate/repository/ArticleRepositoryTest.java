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
    void itShouldCheckIfArticleExistsByIdAndName() {
        //given
        Article article = new Article(
                5,
                "Lunettes",
                500
        );
        underTest.save(article);

        //when
        boolean doesExist = underTest.doesArticleExist(5, "Lunettes");

        //then
        assertThat(doesExist).isTrue();
    }

    @Test
    void itShouldCheckIfArticleNameDoesNotExist() {
        //given
        String name = "Cahier";

        //when
        boolean doesExist = underTest.doesArticleExistByName(name);

        //then
        assertThat(doesExist).isFalse();
    }
}