package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Slide;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Programmer   : pancara
 * Date         : 7/10/11
 * Time         : 3:22 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class SlideServiceImplTest {
    @Autowired
    private SlideService slideService;

    @Test
    public void testGetNext() throws Exception {
        Slide next = slideService.getNext(2L);
        System.out.println("next.getId() = " + next.getId());
    }

    @Test
    public void reindexAlls() {
        slideService.reindexAlls();
    }

    @Test
    public void jsonSlideObjectRender() throws IOException {
        Slide slide = slideService.findById(1L);

        StringWriter sw = new StringWriter();   // serialize
        ObjectMapper mapper = new ObjectMapper();
        MappingJsonFactory jsonFactory = new MappingJsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(sw);
        mapper.writeValue(jsonGenerator, slide);
        sw.close();
        System.out.println(sw.getBuffer().toString());
    }

    @Test
    public void slideToViewRendering() throws IOException {
        Slide slide = slideService.findById(1L);

        MappingJacksonJsonView view = new MappingJacksonJsonView();
        view.addStaticAttribute("slide", slide);
        StringWriter sw = new StringWriter();   // serialize
        sw.close();
        System.out.println(sw.getBuffer().toString());
    }

    @Test
    public void normalizeIndex() {
        slideService.reindexAlls();
    }

}
