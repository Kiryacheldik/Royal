package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Image;
import com.epam.kirillcheldishkin.entity.UserProposal;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.service.UserService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProposeImageCommand implements Command{
    private static final String LOAD_DIR = "/Users/kirillceldyskin/Documents/uploads";
    private static final Lock LOCK = new ReentrantLock();
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ProposeImageCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        int userId = ((User)session.getAttribute("user")).getId();
        Image proposalImage;
        UserService userService = factory.getUserService();
        try {//Now we can propose only one image, but this code has written to have possibility loading lots of images
            for (Part part : request.getParts()) {
                LOCK.lock();
                String fileName = extractFileName(request);
                proposalImage = new Image(fileName);
                part.write(LOAD_DIR + File.separator + fileName);
                userService.proposeImage(proposalImage, userId);
                LOCK.unlock();
            }
            List<UserProposal> proposals = userService.findProposalsByUserId(userId);
            request.setAttribute("proposals", proposals);
            content.setRouter(new Router(Router.Type.FORWARD, "WEB-INF/view/user-page.jsp"));
        } catch (IOException | ServletException | ServiceException | SQLException e) {
            LOGGER.error("Failed while tried to load proposal from user with id --> " + userId);
            content.setRouter(new Router(Router.Type.FORWARD, "WEB-INF/view/error.jsp"));
        }
        return content;
    }

    private String extractFileName(HttpServletRequest request) {
        String proposeName = "user_id=" + ((User)request.getSession().getAttribute("user")).getId();
        String currentDate = LocalDate.now().toString();
        String currentTime = LocalTime.now().toString().replace(":", ".");
        return proposeName + "_" + currentDate + "_" + currentTime + ".JPG";
    }
}
