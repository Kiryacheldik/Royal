package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.UserProposal;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ToUserProposalsPageCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ToUserProposalsPageCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        int userId = ((User)session.getAttribute("user")).getId();
        UserService service = factory.getUserService();
        try {
            List<UserProposal> proposals = service.findProposalsByUserId(userId);
            request.setAttribute("proposals", proposals);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/proposals-page.jsp"));
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to find proposals for user with id --> " + userId);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error.jsp"));
        }
        return content;
    }
}
