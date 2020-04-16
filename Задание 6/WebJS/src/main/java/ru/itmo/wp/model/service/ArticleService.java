package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(String title, String text, User user) throws ValidationException {
        if (Strings.isNullOrEmpty(title)) {
            throw new ValidationException("Title is required");
        }
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Text is required");
        }
        if (title.length() >= 100) {
            throw new ValidationException("Title must be shorter then 100 symbols.");
        }
        if (text.length() >= 1000) {
            throw new ValidationException("Text must be shorter then 1000 symbols.");
        }
        if (null == user) {
            throw new ValidationException("You should been authorized.");
        }
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllById(long userId) {
        return articleRepository.findAllById(userId);
    }

    public void changeHidden(long id, boolean hide) {
        articleRepository.changeHidden(id, hide);
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }
}
