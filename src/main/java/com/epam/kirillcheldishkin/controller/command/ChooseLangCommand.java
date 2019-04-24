package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;

public class ChooseLangCommand implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent=new ResponseContent();
        String locale=request.getParameter("local");
        ServletContext context = request.getServletContext();
        context.setAttribute("locale",locale);
        responseContent.setRouter(new Router(Router.Type.FORWARD,"index.jsp"));
        return responseContent;
    }
}
