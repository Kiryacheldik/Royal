package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.user.UserRole;
import com.epam.kirillcheldishkin.service.DiscountCardService;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import com.epam.kirillcheldishkin.util.DiscountCardNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class GiveDiscountCardForUserCommand implements Command{
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(GiveDiscountCardForUserCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        int discount = Integer.parseInt(request.getParameter("discount"));
        int number = DiscountCardNumber.getInstance().getNumber();
        int userId = Integer.parseInt(request.getParameter("userId"));
        DiscountCardService discountCardService = factory.getDiscountCardService();
        try {
            discountCardService.saveCard(userId, number, discount);
            return new ToAllUsersPageCommand().execute(request);
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to give new discount card " + e);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
        }
        return content;
    }
}
