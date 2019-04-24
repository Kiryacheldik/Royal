package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Tattoo;
import com.epam.kirillcheldishkin.service.TattooService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SearchTattooCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(SearchTattooCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        TattooService tattooService = factory.getTattooService();
        String name = setName(request);
        try {
            List<Tattoo> foundTattoos;
            if (name == null) {
                foundTattoos = tattooService.findAll();
            } else {
                foundTattoos = tattooService.findByNameLike(request.getParameter("name"));
            }
            session.setAttribute("tattoos", foundTattoos);
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/tattoos-page.jsp"));
        } catch (ServiceException e) {
            LOGGER.error("Failed while tried to find tattoos which have '" + name + "' in their name");
            content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/error-page.jsp"));
        }
        return content;
    }

    private String setName(HttpServletRequest request) {
        if (request.getParameter("name").equals("")) {
            return null;
        } else {
            return request.getParameter("name");
        }
    }
}
