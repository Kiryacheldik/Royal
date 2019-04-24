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
import java.util.List;

public class ToUserOrdersPageCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ToUserOrdersPageCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        int userId = ((User)session.getAttribute("user")).getId();
        OrderService orderService = factory.getOrderService();
        try {
            List<Order> orderList = orderService.findOrdersByUserId(userId);
            request.setAttribute("orders", orderList);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/orders-page.jsp"));
        } catch (ServiceException | SQLException e) {
            LOGGER.error("Failed while tried to load orders for user with id --> " + userId);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error.jsp"));
        }
        return content;
    }
}
