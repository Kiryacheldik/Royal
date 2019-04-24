package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.user.UserRole;
import com.epam.kirillcheldishkin.service.DiscountCardService;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ToAllUsersPageCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ToAllUsersPageCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        try {
            UserService userService = factory.getUserService();
            DiscountCardService discountCardService = factory.getDiscountCardService();
            List<Integer> discounts = discountCardService.findAllDiscounts();
            List<User> clients = new ArrayList<>();
            for (User user : userService.findAll()) {
                if (user.getRole().equals(UserRole.CLIENT)) {
                    clients.add(user);
                }
            }
            request.setAttribute("discounts", discounts);
            request.setAttribute("clients", clients);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/admin-users-page.jsp"));
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to load clients and discounts from data base");
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
        }
        return content;
    }
}
