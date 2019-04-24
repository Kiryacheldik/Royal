package com.epam.kirillcheldishkin.dto;

import com.epam.kirillcheldishkin.controller.command.Router;

public class ResponseContent {
    private Router router;

    public void setRouter(Router router){
        this.router=router;
    }

    public Router getRouter(){
        return router;
    }
}
