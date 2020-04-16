package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        //no operations
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("currentUser", userService.find(user.getId()));
        }
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void setAdmin(HttpServletRequest request, Map<String, Object> view) {
        long id = Long.parseLong(request.getParameter("userId"));
        User user = userService.find(id);
        boolean value = Boolean.parseBoolean(request.getParameter("value"));
        User currentUser = (User) request.getSession().getAttribute("user");
        userService.setAdmin(id, value);
        if (id == currentUser.getId()) {
            view.put("usersRedirect", true);
        }
    }
}
