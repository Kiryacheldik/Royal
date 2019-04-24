package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;

import javax.servlet.http.HttpServletRequest;

public class ReturnToBackPageCommand implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        content.setRouter(new Router(Router.Type.FORWARD, request.getParameter("page")));
        return content;
    }
}
