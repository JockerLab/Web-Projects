package ru.itmo.wp.web.page;

import org.checkerframework.checker.units.qual.A;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            request.getSession().setAttribute("message", "You should been authorized");
            throw new RedirectException("/index");
        }
    }

    private void article(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Article article = new Article();
        String title = request.getParameter("title");
        article.setTitle(title);
        String text = request.getParameter("text");
        article.setText(text);

        User user = (User) request.getSession().getAttribute("user");
        articleService.validateArticle(title, text, user);
        request.getSession().setAttribute("message", "You successfully created an article");
        article.setUserId(user.getId());
        articleService.save(article);

        throw new RedirectException("/index");
    }
}
