package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;

import javax.servlet.http.HttpServletRequest;

public class ToTattooCreatorPageCommand implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/tattoo-creator-page.jsp"));
        return content;
    }
}
