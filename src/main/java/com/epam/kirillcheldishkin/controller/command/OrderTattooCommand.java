package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Order;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.service.OrderService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class OrderTattooCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(OrderTattooCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        int tattooId = Integer.parseInt(request.getParameter("tattooId"));
        int userId = ((User)session.getAttribute("user")).getId();
        OrderService orderService = factory.getOrderService();
        try {
            orderService.save(new Order(userId, tattooId));
            request.setAttribute("orders", orderService.findOrdersByUserId(userId));
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/user-page.jsp"));
        } catch (ServiceException | SQLException e) {
            LOGGER.error("Failed while tried to order tattoo with id --> " + tattooId + " from user with id --> " + userId);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error.jsp"));
        }
        return content;
    }
}
