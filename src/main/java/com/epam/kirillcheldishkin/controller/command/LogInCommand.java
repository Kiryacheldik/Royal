package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.user.UserRole;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogInCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(LeaveRatingCommand.class);


    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        UserService service = factory.getUserService();
        try {
            User user = service.logIn(login, password);
            if (user != null) {
                session.setAttribute("user", user);
                if (user.getRole().equals(UserRole.CLIENT)) {
                    content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/user-page.jsp"));
                } else {
                    content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/admin-page.jsp"));
                }
            } else {
                request.setAttribute("loginError", "true");
                content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/login.jsp"));
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to log in user with login --> " + login + " and password --> " + password);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error.jsp"));
        }
        return content;
    }
}
