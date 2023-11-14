package com.ynov.boilerplate.services;

import com.ynov.boilerplate.config.autoincrement.SequenceGeneratorService;
import com.ynov.boilerplate.entity.Article;
import com.ynov.boilerplate.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private ArticleService underTest;

    @Test
    void canGetAllArticle() {
        //when
        underTest.getAllArticle();
        //then
        verify(articleRepository).findAll();
    }

    @Test
    void canCreateArticle() {
        // given
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

        // when
        Article savedArticle = underTest.createArticle(article);

        // then
        verify(sequenceGeneratorService, times(1)).getNextSequence("article_sequence");
        verify(articleRepository, times(1)).save(any(Article.class));

        assertEquals(1, savedArticle.getId());
    }


    @Test
    void deleteArticleById() {
        // Arrange
        int articleId = 1;

        // Act
        underTest.deleteArticleById(articleId);

        // Assert
        verify(articleRepository, times(1)).deleteById(articleId);
    }

    @Test
    void testFindArticleById() {
        // Arrange
        int articleId = 1;
        Article mockArticle = new Article();
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(mockArticle));

        // Act
        Article result = underTest.findArticlebyId(articleId);

        // Assert
        assertEquals(mockArticle, result);
    }

    @Test
    void testFindArticleByIdAndName() {
        // Arrange
        int articleId = 1;
        String articleName = "Chargeur";
        Article mockArticle = new Article();
        when(articleRepository.findArticleByIdAndName(articleId, articleName)).thenReturn(mockArticle);

        // Act
        Article result = underTest.findArticlebyIdAndName(articleId, articleName);

        // Assert
        assertEquals(mockArticle, result);
    }

    @Test
    void testUpdateArticle() {
        // Arrange
        int articleId = 1;
        Article existingArticle = new Article();
        existingArticle.setId(articleId);
        existingArticle.setName("OldName");
        existingArticle.setPrice(50);

        Article updatedArticle = new Article();
        updatedArticle.setName("NewName");
        updatedArticle.setPrice(75);

        Optional<Article> optionalArticle = Optional.of(existingArticle);
        when(articleRepository.findById(articleId)).thenReturn(optionalArticle);

        // Act
        Article result = underTest.updateArticle(articleId, updatedArticle);

        // Assert
        assertNotNull(result);
        assertEquals(articleId, result.getId());
        assertEquals(updatedArticle.getName(), result.getName());
        assertEquals(updatedArticle.getPrice(), result.getPrice());

        // Verify that save method was called with the updated article
        verify(articleRepository, times(1)).save(result);
    }
}