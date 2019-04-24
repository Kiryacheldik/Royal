package com.epam.kirillcheldishkin.controller.command;

import com.epam.kirillcheldishkin.dao.exception.DaoNotFoundException;
import com.epam.kirillcheldishkin.dto.ResponseContent;
import com.epam.kirillcheldishkin.entity.Image;
import com.epam.kirillcheldishkin.entity.Tattoo;
import com.epam.kirillcheldishkin.entity.TattooImage;
import com.epam.kirillcheldishkin.entity.imageType.ImageType;
import com.epam.kirillcheldishkin.service.ImageService;
import com.epam.kirillcheldishkin.service.TattooService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CreateTattooCommand implements Command{
    private static final String LOAD_DIR = "/Users/kirillceldyskin/Documents/uploads";
    private static final Lock LOCK = new ReentrantLock();
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(CreateTattooCommand.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent content = new ResponseContent();
        HttpSession session = request.getSession();
        File fileSaveDir = new File(LOAD_DIR);
        if(!fileSaveDir.exists()){
            fileSaveDir.mkdir();
        }
        try {
            List<Part> fileParts = request.getParts().stream()
                    .filter(part -> (!"name".equals(part.getName()) && !"".equals(part.getSubmittedFileName())))
                    .collect(Collectors.toList());
            Tattoo tattoo = new Tattoo(request.getParameter("name"));
            boolean imageExists = false;

            TattooService tattooService = factory.getTattooService();
            ImageService imageService = factory.getImageService();

            List<Image> images = imageService.findAll();

            for (Part part : fileParts) {
                LOCK.lock();
                for (Image image : images) {
                    if (image.getImage().equals(part.getSubmittedFileName()) && image.getImage().startsWith("user_id")) {
                        imageExists = updateImageName(image, request, part, imageService);
                        addImage(tattoo, image.getImage(), part.getName());
                    }
                }
                if (!imageExists) {
                    String fileName = extractFileName(request, part);
                    addImage(tattoo, fileName, part.getName());
                    part.write(LOAD_DIR + File.separator + fileName);
                } else {
                    imageExists = false;
                }
                LOCK.unlock();
            }
            tattooService.saveTattoo(tattoo);
            content.setRouter(new Router(Router.Type.FORWARD, "WEB-INF/view/admin-page.jsp"));
        } catch (IOException | ServletException | DaoNotFoundException | SQLException | ServiceException e) {
            LOGGER.error("Failed while tried to create new tattoo " + e);
            content.setRouter(new Router(Router.Type.FORWARD, "WEB-INF/view/error.jsp"));
        }
        return content;
    }

    private String extractFileName(HttpServletRequest request, Part part) {
        String tattooName = request.getParameter("name");
        tattooName += "_" + part.getName();
        String currentDate = LocalDate.now().toString();
        String currentTime = LocalTime.now().toString().replace(":", ".");
        return tattooName + "_" + currentDate + "_" + currentTime + ".JPG";
    }

    private void addImage(Tattoo tattoo, String imageName, String type) {
        TattooImage image;
        switch (type) {
            case "titleImage":
                image = new TattooImage(imageName);
                image.setType(ImageType.TITLE);
                tattoo.getImages().add(image);
                break;
            case "ordinalImage":
                image = new TattooImage(imageName);
                image.setType(ImageType.ORDINARY);
                tattoo.getImages().add(image);
                break;
            case "sketchImage":
                image = new TattooImage(imageName);
                image.setType(ImageType.SKETCH);
                tattoo.getImages().add(image);
                break;
        }
    }

    private boolean updateImageName(Image image, HttpServletRequest request, Part part, ImageService imageService) throws ServiceException{
        File file = new File(LOAD_DIR, image.getImage());
        String newFileName = extractFileName(request, part);
        image.setImage(newFileName);
        file.renameTo(new File(LOAD_DIR + File.separator + newFileName));
        imageService.renameImage(image);
        return true;
    }
}
