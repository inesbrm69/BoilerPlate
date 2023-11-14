package com.ynov.boilerplate.services;

import com.ynov.boilerplate.config.autoincrement.SequenceGeneratorService;
import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.repository.ArticleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private ArticleService underTest;

    /*@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }*/

    @Test
    void canGetAllArticle() {
        //when
        underTest.getAllArticle();
        //then
        verify(articleRepository).findAll();
    }

    @Test
    void canCreateArticle() {
        // Arrange
        Article article = new Article();
        article.setName("Chargeur");
        article.setPrice(35);

        // Configure the mock
        when(sequenceGeneratorService.getNextSequence("article_sequence")).thenReturn(1);
        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> {
            Article saved = invocation.getArgument(0);
            saved.setId(1);
            return saved;
        });

        // Act
        Article savedArticle = underTest.createArticle(article);

        // Assert
        verify(sequenceGeneratorService, times(1)).getNextSequence("article_sequence");
        verify(articleRepository, times(1)).save(any(Article.class));

        // Assert that the ID is updated in the returned article
        assertEquals(1, savedArticle.getId());
    }


    @Test
    @Disabled
    void deleteArticleById() {
    }
}