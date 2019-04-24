package com.epam.kirillcheldishkin.controller;

import com.epam.kirillcheldishkin.controller.command.Command;
import com.epam.kirillcheldishkin.controller.command.CommandProvider;
import com.epam.kirillcheldishkin.controller.command.Router;
import com.epam.kirillcheldishkin.dto.ResponseContent;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/front")
@MultipartConfig()
public class FrontServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandProvider.getInstance().takeCommand(request.getParameter("command"));
        if(command==null){
            if (request.getParameter("command").equals("image")) {
                takeImageFormBd(request, response);
            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else {
            ResponseContent responseContent = command.execute(request);
            if(responseContent.getRouter().getType()== Router.Type.REDIRECT){
                response.sendRedirect(responseContent.getRouter().getRoute());
            }else{
                request.getRequestDispatcher(responseContent.getRouter().getRoute()).forward(request,response);
            }
        }
    }

    private void takeImageFormBd(HttpServletRequest request, HttpServletResponse response) throws IOException{
        File fileDir = new File("/Users/kirillceldyskin/Documents/uploads");
            FileInputStream stream = new FileInputStream(fileDir + File.separator + request.getParameter("name"));
            BufferedInputStream bis = new BufferedInputStream(stream);
            response.setContentType("image/jpeg");
            BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
            for (int data; (data = bis.read()) > -1;) {
                output.write(data);
            }
    }
}
