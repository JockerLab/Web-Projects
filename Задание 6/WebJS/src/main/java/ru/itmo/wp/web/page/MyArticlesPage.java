package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            request.getSession().setAttribute("message", "You should been authorized");
            throw new RedirectException("/index");
        }
    }

    private void findMyArticles(HttpServletRequest request, Map<String, Object> view) {
        view.put("myArticles", articleService.findAllById(((User) request.getSession().getAttribute("user")).getId()));
    }

    private void changeHidden(HttpServletRequest request, Map<String, Object> view) {
        long id = Long.parseLong(request.getParameter("articleId"));
        Article article = articleService.find(id);
        boolean hide = article.isHidden();
        User user = (User) request.getSession().getAttribute("user");
        if (user.getId() != article.getUserId()) {
            request.getSession().setAttribute("message", "Bad owner");
            throw new RedirectException("/index");
        }
        articleService.changeHidden(id, hide);
        view.put("currentHide", !hide);
    }
}
