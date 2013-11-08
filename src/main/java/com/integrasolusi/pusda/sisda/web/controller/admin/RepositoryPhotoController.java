package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Photo;
import com.integrasolusi.pusda.sisda.service.PhotoService;
import com.integrasolusi.pusda.sisda.web.form.PhotoSearchForm;
import com.integrasolusi.pusda.sisda.web.form.RepositoryPhotoForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 2:07 PM
 */
@Controller("adminRepositoryPhotoContoller")
@RequestMapping("/admin/repo/photo")
public class RepositoryPhotoController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RepositoryPhotoController.class);

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") PhotoSearchForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") PhotoSearchForm form) {
        ModelAndView mav = new ModelAndView("admin/repo/photo/list");
        mav.addObject("current", page);

        Long count = getCount(form);
        mav.addObject("count", count);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);
        mav.addObject("last", pagingHelper.calcPageCount(count));
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));

        List<Photo> photoList = getPhotoList(form, start, pagingHelper.getRowPerPage());
        mav.addObject("photoList", photoList);

        return mav;
    }

    private Long getCount(PhotoSearchForm form) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return photoService.countAlls();
        else {
            return photoService.countByKeyword(getSearchKeyword(form));
        }
    }

    private String getSearchKeyword(PhotoSearchForm form) {
        return String.format("%%%s%%", form.getKeyword());
    }

    private List<Photo> getPhotoList(PhotoSearchForm form, Long start, Long count) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return photoService.findAlls(start, count);
        else
            return photoService.findByKeyword(getSearchKeyword(form), start, count);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") RepositoryPhotoForm form) throws IOException {
        return new ModelAndView("admin/repo/photo/form");
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") RepositoryPhotoForm form,
                            Errors errors) throws IOException {
        savePhotoForm(null, form);
        return createRedirectView(form.getTitle());
    }

    private ModelAndView createRedirectView(String keyword) {
        return new ModelAndView(String.format("redirect:/admin/repo/photo/list.html?keyword=%s", keyword));
    }

    private void savePhotoForm(Long id, RepositoryPhotoForm form) throws IOException {
        Photo photo = id == null ? new Photo() : photoService.findById(id);
        photo.setTitle(form.getTitle());
        if (form.getFile() != null && !form.getFile().isEmpty()) {
            photo.setFilename(form.getFile().getOriginalFilename());
            photoService.save(photo, form.getFile().getInputStream());
        } else {
            photoService.save(photo);
        }
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") RepositoryPhotoForm form) throws IOException {
        Photo photo = photoService.findById(id);
        form.setTitle(photo.getTitle());

        ModelAndView mav = new ModelAndView("admin/repo/photo/form");
        mav.addObject("photo", photo);

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView submitEdit(@PathVariable("id") Long id,
                                   @ModelAttribute("form") RepositoryPhotoForm form,
                                   Errors errors) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            ModelAndView mav = new ModelAndView("admin/repo/photo/form");
            Photo photo = photoService.findById(id);
            mav.addObject("photo", photo);
            return mav;
        }
        savePhotoForm(id, form);
        return createRedirectView(form.getTitle());
    }

    private void validateForm(RepositoryPhotoForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul belum diisi");
        }
    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Photo photo = photoService.findById(id);
        if (photo != null) {
            response.setContentType(contentTypeUtils.getContentType(photo.getFilename()));
            photoService.getBlob(id, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @RequestMapping("thumb/{id}/**")
    public void thumb(@PathVariable("id") Long id,
                      @RequestParam(value = "width", required = false, defaultValue = "100") Integer width,
                      @RequestParam(value = "height", required = false, defaultValue = "0") Integer height,
                      HttpServletResponse response) throws IOException {
        Photo photo = photoService.findById(id);
        if (photo != null) {
            response.setContentType(contentTypeUtils.getContentType(photo.getFilename()));
            photoService.getBlob(id, width, height, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @RequestMapping(value = "manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return new ModelAndView(String.format("redirect:/admin/repo/photo/page/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {

        if (ids != null) {
            photoService.removeByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/repo/photo/page/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            photoService.publishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/repo/photo/page/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            photoService.unpublishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/repo/photo/page/%d.html?keyword=%s", page, keyword));

    }
}
