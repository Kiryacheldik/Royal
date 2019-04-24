package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Client;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.UserRegisterException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import com.epam.kirillcheldishkin.validation.exception.LoginIsAlreadyUsedException;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class RegisterUserCommand implements Command{
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(RegisterUserCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        Client user = new Client(email, login, password, username, 0);
        UserService userService = factory.getUserService();
        HttpSession session = request.getSession();
        try {
            userService.signIn(user);
            session.setAttribute("user", user);
            content.setRouter(new Router(Router.Type.REDIRECT, request.getContextPath()+"?command=user-page"));
        } catch (LoginIsAlreadyUsedException e) {
            LOGGER.error("Failed registration because " + login + " is already used by another user");
            request.setAttribute("loginError", "true");
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/registration.jsp"));
        } catch (UserRegisterException | ServiceException | SQLException e) {
            LOGGER.error("Failed while tried to register user --> " + user);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error.jsp"));
        }
        return content;
    }
}
