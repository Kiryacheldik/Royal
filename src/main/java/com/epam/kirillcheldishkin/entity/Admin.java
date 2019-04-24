package com.epam.kirillcheldishkin.entity;

import com.epam.kirillcheldishkin.entity.user.UserRole;

public class Admin extends User {
    public Admin(String email, String login, String password, String username) {
        super(email, login, password, username, UserRole.ADMIN);
    }
}
