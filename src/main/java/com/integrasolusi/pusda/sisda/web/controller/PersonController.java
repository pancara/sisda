package com.integrasolusi.pusda.sisda.web.controller;

import com.integrasolusi.pusda.sisda.persistence.Person;
import com.integrasolusi.pusda.sisda.service.PersonService;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Programmer   : pancara
 * Date         : 7/22/11
 * Time         : 11:42 PM
 */
@Controller
public class PersonController {
    private PersonService personService;
    private StreamHelper streamHelper;
    private ContentTypeUtils contentTypeUtils;


    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setStreamHelper(StreamHelper streamHelper) {
        this.streamHelper = streamHelper;
    }

    @RequestMapping("/person/photo/{id}.html")
    public void photo(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        if (personService.photoExist(id)) {
            Person person = personService.findById(id);
            String contentType = contentTypeUtils.getContentType(person.getPhotoFilename());
            response.setContentType(contentType);

            if (person.getPhotoSize() != null) {
                response.setContentLength(person.getPhotoSize().intValue());
            }
            personService.getPhoto(id, response.getOutputStream());
        } else {
            defaultPhoto(response);
        }

    }

    @RequestMapping("/person/photo.html")
    public void defaultPhoto(HttpServletResponse response) throws IOException {
        String unavailablePhoto = "/images/photo-unavailable.jpg";
        java.io.InputStream source = new ClassPathResource(unavailablePhoto).getInputStream();
        String contentType = contentTypeUtils.getContentType(unavailablePhoto);
        response.setContentType(contentType);

        streamHelper.copy(source, response.getOutputStream());
        response.flushBuffer();
        source.close();

    }

}
