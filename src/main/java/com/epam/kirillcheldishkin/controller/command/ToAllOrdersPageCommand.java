package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Order;
import com.epam.kirillcheldishkin.service.OrderService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToAllOrdersPageCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        try {
            OrderService orderService = factory.getOrderService();
            List<Order> orderList = orderService.findAll();
            request.setAttribute("orders", orderList);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/admin-orders-page.jsp"));
        } catch (ServiceException | SQLException e) {
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error.jsp"));
        }
        return content;
    }
}
