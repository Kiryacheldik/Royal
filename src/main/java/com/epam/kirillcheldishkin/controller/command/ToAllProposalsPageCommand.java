package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.UserProposal;
import com.epam.kirillcheldishkin.service.UserProposalService;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class ToAllProposalsPageCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ToAllProposalsPageCommand.class);
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        try {
            UserProposalService service = factory.getUserProposalService();
            List<UserProposal> proposals = service.findAllProposals();
            request.setAttribute("proposals", proposals);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/admin-proposals-page.jsp"));
        } catch (ServiceException | SQLException e) {
            LOGGER.error("Failed while tried to load all proposals from data base");
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error.jsp"));
        }
        return content;
    }
}
