package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.user.UserRole;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import com.epam.kirillcheldishkin.util.PasswordEncryptor;
import com.epam.kirillcheldishkin.validation.exception.InvalidUserPasswordException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeUserPasswordCommand implements Command{
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ChangeUserPasswordCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        User user = ((User)session.getAttribute("user"));
        String userPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        UserService userService = factory.getUserService();
        try {
            userService.changePassword(user, userPassword, newPassword);
            session.setAttribute("user", userService.findById(user.getId()));
            request.setAttribute("incorrectPassword", false);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/change-user-password.jsp"));
        } catch (InvalidUserPasswordException e) {
            LOGGER.error("user --> " + user + " has another password");
            request.setAttribute("incorrectPassword", true);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/change-user-password.jsp"));
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to change password for user --> " + user);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
        }
        return content;
    }
}
