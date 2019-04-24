package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Client;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.user.UserStatus;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BlockUserCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(BlockUserCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        int userId = Integer.parseInt(request.getParameter("userId"));
        UserService userService = factory.getUserService();
        try {
            User client = userService.findById(userId);
            ((Client)client).setActiveStatus(UserStatus.BLOCKED);
            userService.updateClientStatus((Client) client);
            return new ToAllUsersPageCommand().execute(request);
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to block user");
            content.setRouter(new Router(Router.Type.FORWARD, "WEB-INF/view/error-page.jsp"));
        }
        return content;
    }
}
