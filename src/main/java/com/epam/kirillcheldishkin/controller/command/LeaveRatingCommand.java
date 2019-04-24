package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LeaveRatingCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(LeaveRatingCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        int userId = ((User)session.getAttribute("user")).getId();
        int tattooId = Integer.parseInt(request.getParameter("tattooId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        if (rating < 0) {
            rating = 0;
        }
        if (rating > 5) {
            rating = 5;
        }//other method
        UserService userService = factory.getUserService();
        try {
            userService.addRatingToTattoo(userId, tattooId, rating * 20);
            return new ToTattoosPageCommand().execute(request);
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to leave rating for tattoo with id -->" + tattooId + ", from user with id --> " + userId);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
        }
        return content;
    }
}
