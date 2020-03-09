package com.vardanmk.ChatServiceApp.controller;

import com.vardanmk.ChatServiceApp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class ControllerUtils {

    private static final Logger log = LoggerFactory.getLogger(ControllerUtils.class);
    static void saveFile(@Valid User user, @RequestParam("file") MultipartFile file ,String uploadPath) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            log.info("picture saved to " + uploadPath + "/" + resultFilename);
            user.setFilename(resultFilename);
        }
    }
}
