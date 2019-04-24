package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.UserProposal;
import com.epam.kirillcheldishkin.entity.state.SubmittedState;
import com.epam.kirillcheldishkin.service.UserProposalService;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class MakeNextProposalStepCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(LeaveRatingCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        int proposalId = Integer.parseInt(request.getParameter("id"));
        boolean accepted = Boolean.parseBoolean(request.getParameter("accepted"));
        UserService userService = factory.getUserService();
        int rating = setRating(request);
        UserProposalService proposalService = factory.getUserProposalService();
        try {
            UserProposal proposal = proposalService.findById(proposalId);
            proposal.setRating(rating);
            ((SubmittedState)proposal.getState()).setAccepted(accepted);
            proposal.setState(proposal.getState().nextState());
            if (accepted) {
                userService.acceptProposal(proposal, proposal.getRating());
            } else {
                userService.cancelProposal(proposal);
            }
            request.setAttribute("proposals", proposalService.findAllProposals());
            content.setRouter(new Router(Router.Type.FORWARD, "WEB-INF/view/admin-proposals-page.jsp"));

        } catch (ServiceException | SQLException e) {
            LOGGER.error("Failed while tried to make next state in proposal with id --> " + proposalId);
            content.setRouter(new Router(Router.Type.FORWARD, "WEB-INF/view/error.jsp"));
        }
        return content;
    }

    private int setRating(HttpServletRequest request) {
        if (request.getParameter("rating") != null) {
            return Integer.parseInt(request.getParameter("rating"));
        }
        return 0;
    }
}
