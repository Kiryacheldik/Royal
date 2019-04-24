package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Image;
import com.epam.kirillcheldishkin.service.ImageService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ToAllImagesPageCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ToAllImagesPageCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        try {
            ImageService service = factory.getImageService();
            List<Image> imageList = service.findAll();
            request.setAttribute("images", imageList);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/admin-images-page.jsp"));
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to load images from data base");
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
        }
        return content;
    }
}
