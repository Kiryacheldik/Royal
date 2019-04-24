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

public class ChangeUserNameCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ChangeUserNameCommand.class);
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();

        User user = ((User)session.getAttribute("user"));
        String newUsername = request.getParameter("username");
        UserService userService = factory.getUserService();

        try {
            user.setUsername(newUsername);
            userService.update(user);
            session.setAttribute("user", userService.findById(user.getId()));
            if (user.getRole().equals(UserRole.ADMIN)) {
                content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/admin-page.jsp"));
            } else if (user.getRole().equals(UserRole.CLIENT)) {
                content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/user-page.jsp"));
            } else {
                LOGGER.error("This role(" + user.getRole().getRole() + ") isn't registered");
                content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to change password for user --> " + user);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
        }
        return content;
    }
}
