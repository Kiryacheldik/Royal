package com.epam.kirillcheldishkin.service.factory;

import com.epam.kirillcheldishkin.service.*;

public class ServiceFactory {
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return SERVICE_FACTORY;
    }

    public UserService getUserService() {
        return new UserService();
    }

    public UserProposalService getUserProposalService() {
        return new UserProposalService();
    }

    public TattooService getTattooService() {
        return new TattooService();
    }

    public OrderService getOrderService() {
        return new OrderService();
    }

    public DiscountCardService getDiscountCardService() {
        return new DiscountCardService();
    }

    public ImageService getImageService() {
        return new ImageService();
    }
}
