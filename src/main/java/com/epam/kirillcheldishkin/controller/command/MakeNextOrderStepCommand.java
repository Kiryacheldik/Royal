package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Order;
import com.epam.kirillcheldishkin.entity.state.AcceptedState;
import com.epam.kirillcheldishkin.entity.state.SubmittedState;
import com.epam.kirillcheldishkin.entity.state.status.Status;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.user.UserRole;
import com.epam.kirillcheldishkin.service.OrderService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class MakeNextOrderStepCommand implements Command{
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(LeaveRatingCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter("id"));
        int userId = ((User)session.getAttribute("user")).getId();
        boolean accepted = Boolean.parseBoolean(request.getParameter("accepted"));
        Integer rating = setRating(request);
        OrderService orderService = factory.getOrderService();
        try {
            Order order = orderService.findById(orderId);

            setNextState(order, accepted, rating);

            orderService.makeNextStepOrderState(order);

            if (((User)session.getAttribute("user")).getRole() == UserRole.ADMIN) {
                request.setAttribute("orders", orderService.findAll());
                content.setRouter(new Router(Router.Type.REDIRECT, request.getContextPath()+"?command=all-orders"));
            } else {
                request.setAttribute("orders", orderService.findOrdersByUserId(userId));
                content.setRouter(new Router(Router.Type.REDIRECT, request.getContextPath()+"?command=user-orders"));
            }
        } catch (SQLException | ServiceException e) {
            LOGGER.error("Failed while tried to make next state in order with id --> " + orderId + " from user with id --> " + userId);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error.jsp"));
        }
        return content;
    }

    private void setNextState(Order order, boolean accepted, Integer rating) {
        switch (order.getState().getStatus()) {
            case SUBMITTED:
                ((SubmittedState)order.getState()).setAccepted(accepted);
                break;
            case ACCEPTED:
                ((AcceptedState)order.getState()).setAccepted(accepted);
                break;
            case AWAITING_FEED_BACK:
                if (rating == null) {
                    order.setRating(-1);
                } else {
                    order.setRating(rating);
                }//Protection add
                break;
        }
        order.setState(order.getState().nextState());
    }

    private Integer setRating(HttpServletRequest request) {
        if (request.getParameter("rating") == null) {
            return null;
        } else {
            return Integer.parseInt(request.getParameter("rating"));
        }
    }
}
