package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.DiscountCard;
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

public class UpdateDiscountCardCommand implements Command{
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(UpdateDiscountCardCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        int discount = Integer.parseInt(request.getParameter("discount"));
        int cardId = Integer.parseInt(request.getParameter("cardId"));
        UserService userService = factory.getUserService();
        DiscountCardService discountCardService = factory.getDiscountCardService();
        try {
            DiscountCard card = discountCardService.findById(cardId);
            discountCardService.update(card, discount);

            List<User> clients = new ArrayList<>();
            for (User client : userService.findAll()) {
                if (client.getRole().equals(UserRole.CLIENT)) {
                    clients.add(client);
                }
            }
            request.setAttribute("clients", clients);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/admin-users-page.jsp"));
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to update discount card with id --> " + cardId);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
        }
        return content;
    }
}
