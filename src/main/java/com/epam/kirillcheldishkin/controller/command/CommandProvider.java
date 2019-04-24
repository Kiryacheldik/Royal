package com.epam.kirillcheldishkin.controller.command;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private Map<String, Command> commandMap = new HashMap<>();

    public static CommandProvider getInstance() {
        return instance;
    }

    private CommandProvider() {
        commandMap.put("home-page", new ToHomePageCommand());
        commandMap.put("registration-page", new ToRegistrationPageCommand());
        commandMap.put("log-in-page", new ToLogInPageCommand());
        commandMap.put("about-us-page", new ToAboutUsPageCommand());
        commandMap.put("tattoos-page", new ToTattoosPageCommand());
        commandMap.put("register-user", new RegisterUserCommand());
        commandMap.put("login-user", new LogInCommand());
        commandMap.put("user-page", new ToUserPageCommand());
        commandMap.put("admin-page", new ToAdminPageCommand());
        commandMap.put("log-out-page", new LogOutCommand());
        commandMap.put("all-users", new ToAllUsersPageCommand());
        commandMap.put("all-orders", new ToAllOrdersPageCommand());
        commandMap.put("all-images", new ToAllImagesPageCommand());
        commandMap.put("all-tattoos", new ToAllTattoosPageCommand());
        commandMap.put("all-proposals", new ToAllProposalsPageCommand());
        commandMap.put("user-orders", new ToUserOrdersPageCommand());
        commandMap.put("user-proposals", new ToUserProposalsPageCommand());
        commandMap.put("propose-image-page", new ToProposalCreatePageCommand());
        commandMap.put("lang", new ChooseLangCommand());
        commandMap.put("back", new ReturnToBackPageCommand());
        commandMap.put("tattoo-creator", new ToTattooCreatorPageCommand());
        commandMap.put("error-authenticate-page", new ToAuthenticationErrorPageCommand());
        commandMap.put("create-tattoo", new CreateTattooCommand());
        commandMap.put("propose-image", new ProposeImageCommand());
        commandMap.put("order-tattoo", new OrderTattooCommand());
        commandMap.put("next-order-step", new MakeNextOrderStepCommand());//filter
        commandMap.put("next-proposal-step", new MakeNextProposalStepCommand());//filter
        commandMap.put("search-tattoo", new SearchTattooCommand());
        commandMap.put("give-card", new GiveDiscountCardForUserCommand());
        commandMap.put("update-card", new UpdateDiscountCardCommand());
        commandMap.put("leave-rating", new LeaveRatingCommand());
        commandMap.put("block-user", new BlockUserCommand());
        commandMap.put("unBlock-user", new UnBlockUserCommand());
        commandMap.put("change-password-page", new ToChangePasswordPage());
        commandMap.put("change-password", new ChangeUserPasswordCommand());
        commandMap.put("change-username", new ChangeUserNameCommand());
    }

    /**
     * Return command by name
     * @param commandType name
     * @return command implementation
     */
    public Command takeCommand(String commandType) {
        return commandMap.get(commandType);
    }
}
