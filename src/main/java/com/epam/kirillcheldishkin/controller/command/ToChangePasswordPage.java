package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;
import javax.servlet.http.HttpServletRequest;

public class ToChangePasswordPage implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        content.setRouter(new Router(Router.Type.FORWARD, "/WEB-INF/view/change-user-password.jsp"));
        return content;
    }
}
