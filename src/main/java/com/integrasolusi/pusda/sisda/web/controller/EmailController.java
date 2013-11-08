package com.integrasolusi.pusda.sisda.web.controller;

import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.StringHelper;
import org.apache.log4j.Logger;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Programmer   : pancara
 * Date         : 7/23/11
 * Time         : 9:44 AM
 */
@Controller
public class EmailController {
    private static Logger logger = Logger.getLogger(EmailController.class);
    private TextEncryptor textEncryptor;
    private ImageUtils imageUtils;

    @Autowired
    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Autowired
    public void setTextEncryptor(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @RequestMapping(value = "/email/{hexCode}.html", method = RequestMethod.GET)
    public void email(HttpServletResponse response, @PathVariable("hexCode") String hexCode) {
        String code = StringHelper.hexToString(hexCode);
        String email = textEncryptor.decrypt(code);
        try {
            imageUtils.createTextImage(email, response.getOutputStream());
        } catch (Exception e) {
            logger.error(e);
        }
    }


}
