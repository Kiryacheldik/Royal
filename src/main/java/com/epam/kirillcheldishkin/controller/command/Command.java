package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dto.ResponseContent;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    ResponseContent execute(HttpServletRequest request);
}
