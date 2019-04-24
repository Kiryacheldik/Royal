package com.epam.kirillcheldishkin.controller.filter;

import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.user.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static java.util.Objects.nonNull;

@WebFilter("/front")
public class AuthenticateFilter implements Filter {
    private Set<String> adminCommands;
    private Set<String> clientCommands;
    private Set<PropertyCommand> commands;
    private static final Logger LOGGER = LogManager.getLogger(AuthenticateFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Properties properties;
        try {//classLoader
            FileInputStream inputStream = new FileInputStream("/Users/kirillceldyskin/Downloads/FinalTask/src/main/resources/authentication.filter.properties");
            properties = new Properties();
            properties.load(inputStream);
            commands = new HashSet<>();
            for (String propertyName : properties.stringPropertyNames()) {
                commands.add(new PropertyCommand(propertyName, properties.getProperty(propertyName)));
            }
        } catch (IOException e) {
            LOGGER.error("Failed while tried to load commands, that must be checked");
            throw new ServletException("Failed while tried to load commands, that must be checked");
        }
        findAdminCommands();
        findClientCommands();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        authentication(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void destroy() {
    }

    private void authentication(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (adminCommands.contains(request.getParameter("command"))) {
            authenticateAdmin(request.getSession(), request, response, chain);
        } else if (clientCommands.contains(request.getParameter("command"))) {
            authenticateClient(request.getSession(), request, response, chain);
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    private void authenticateAdmin(HttpSession session, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        if (nonNull(session) && nonNull(session.getAttribute("user"))) {
            if (((User)session.getAttribute("user")).getRole().equals(UserRole.ADMIN)) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath()+"?command=error-authenticate-page");
            }
        } else {
            response.sendRedirect(request.getContextPath()+"?command=log-in-page");
        }
    }

    private void authenticateClient(HttpSession session, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        if (nonNull(session) && nonNull(session.getAttribute("user"))) {
            if (((User)session.getAttribute("user")).getRole().equals(UserRole.CLIENT)) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath()+"?command=error-authenticate-page");
            }
        } else {
            response.sendRedirect(request.getContextPath()+"?command=log-in-page");
        }
    }

    private void findAdminCommands() {
        adminCommands = new HashSet<>();
        commands.stream()
                .filter(command -> command.getName().startsWith("admin"))
                .forEach(command -> adminCommands.add(command.getCommand()));
    }

    private void findClientCommands() {
        clientCommands = new HashSet<>();
        commands.stream()
                .filter(command -> command.getName().startsWith("client"))
                .forEach(command -> clientCommands.add(command.getCommand()));
    }

    private class PropertyCommand {
        private String name;
        private String command;

        public String getName() {
            return name;
        }

        public String getCommand() {
            return command;
        }

        PropertyCommand(String name, String command) {
            this.name = name;
            this.command = command;
        }
    }
}
