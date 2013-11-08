package com.integrasolusi.pusda.sisda.web.controller;

import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import com.integrasolusi.pusda.sisda.persistence.regulation.Regulation;
import com.integrasolusi.pusda.sisda.service.RegulationFolderService;
import com.integrasolusi.pusda.sisda.service.RegulationService;
import com.integrasolusi.pusda.sisda.web.dto.FolderDto;
import com.integrasolusi.utils.ContentTypeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 11:48 PM
 */
@Controller
public class RegulationController {
    private static Logger logger = Logger.getLogger(RegulationController.class);
    @Autowired
    private RegulationService regulationService;
    @Autowired
    private RegulationFolderService regulationFolderService;
    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("/regulation.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/regulation/list");

        List<FolderDto> folderDtos = new LinkedList<FolderDto>();
        List<Folder> folders = regulationFolderService.findByParent(null);
        for (Folder folder : folders) {
            FolderDto dto = new FolderDto();
            dto.setFolder(folder);
            folderDtos.add(dto);
            
            populateRegulations(dto);
            populateFolderDto(dto);
        }

        mav.addObject("folderDtos", folderDtos);

        return mav;
    }

    private void populateFolderDto(FolderDto parentDto) {
        List<Folder> children = regulationFolderService.findByParent(parentDto.getFolder());
        for (Folder f : children) {
            FolderDto dto = new FolderDto();
            dto.setFolder(f);

            parentDto.getChildren().add(dto);
            populateRegulations(dto);
            populateFolderDto(dto);
        }
    }

    private void populateRegulations(FolderDto dto) {
        dto.setRegulations(regulationService.findByFolder(dto.getFolder()));
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return regulationService.countPublished();
        else
            return regulationService.countPublishedByKeyword(keyword);
    }


    @RequestMapping("/regulation/{id}/**")
    public void download(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        Regulation regulation = regulationService.findById(id);
        String contentType = contentTypeUtils.getContentType(regulation.getFilename());
        response.setContentType(contentType);

        regulationService.copyContent(id, response.getOutputStream());
    }


}
