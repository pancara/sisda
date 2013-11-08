package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Leaflet;
import com.integrasolusi.pusda.sisda.persistence.Ticker;
import com.integrasolusi.pusda.sisda.service.LeafletService;
import com.integrasolusi.pusda.sisda.service.TickerService;
import com.integrasolusi.pusda.sisda.web.form.LeafletForm;
import com.integrasolusi.pusda.sisda.web.form.RepositoryPhotoForm;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 5:19 PM
 */
@Controller("adminLeafletController")
@RequestMapping("/admin")
public class LeafletController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LeafletController.class);
    @Autowired
    private LeafletService leafletService;

    @Autowired
    private TickerService tickerService;

    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("leaflet.html")
    public ModelAndView list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return list(1L, keyword);
    }


    @RequestMapping("leaflet/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("admin/leaflet/list");
        mav.addObject("keyword", keyword);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);

        Long count = getCount(keyword);
        mav.addObject("count", count);

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Leaflet> leafletList = getLeafletlist(keyword, start, pagingHelper.getRowPerPage());
        mav.addObject("leafletList", leafletList);
        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return leafletService.countAlls();
        else
            return leafletService.countByKeyword(keyword);
    }

    private List<Leaflet> getLeafletlist(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return leafletService.get(start, count);
        else
            return leafletService.getByKeyword(keyword, start, count);
    }

    @RequestMapping(value = "leaflet/add.html", method = RequestMethod.GET)
    public ModelAndView createNew(@ModelAttribute("form") LeafletForm form) {
        return new ModelAndView("admin/leaflet/form");
    }

    @RequestMapping(value = "leaflet/add.html", method = RequestMethod.POST)
    public ModelAndView createNew(@ModelAttribute("form") LeafletForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Leaflet leaflet = new Leaflet();
            leaflet.setTitle(form.getTitle());
            leaflet.setDescription(form.getDescription());
            leaflet.setPublishedDate(form.getPublishedDate().getCalendar().getTime());

            leaflet.setPublished(false);
            if (form.getFile() != null && !form.getFile().isEmpty()) {
                leaflet.setFilename(form.getFile().getOriginalFilename());
                leafletService.save(leaflet, form.getFile().getInputStream());
            } else {
                leafletService.save(leaflet);
            }
            return new ModelAndView("redirect:/admin/leaflet.html");
        }
        return new ModelAndView("admin/leaflet/form");
    }

    @RequestMapping(value = "leaflet/edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") LeafletForm form) {
        Leaflet leaflet = leafletService.findById(id);
        form.setTitle(leaflet.getTitle());
        form.setDescription(leaflet.getDescription());

        if (leaflet.getPublishedDate() != null) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(leaflet.getPublishedDate());
            form.getPublishedDate().setCalendar(cal);
        }

        return new ModelAndView("admin/leaflet/form");
    }

    @RequestMapping(value = "leaflet/edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") LeafletForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Leaflet leaflet = leafletService.findById(id);
            leaflet.setTitle(form.getTitle());
            leaflet.setDescription(form.getDescription());
            leaflet.setPublishedDate(form.getPublishedDate().getCalendar().getTime());

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                leaflet.setFilename(form.getFile().getOriginalFilename());
                leafletService.save(leaflet, form.getFile().getInputStream());
            } else {
                leafletService.save(leaflet);
            }
            return new ModelAndView("redirect:/admin/leaflet.html");
        }
        return new ModelAndView("admin/leaflet/form");
    }

    private void validateForm(LeafletForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

    }


    @RequestMapping(value = "leaflet/manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return new ModelAndView(String.format("redirect:/admin/leaflet/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "leaflet/manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            leafletService.removeByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/leaflet/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "leaflet/manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            leafletService.publishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/leaflet/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "leaflet/manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            leafletService.unpublishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/leaflet/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "leaflet/manage.html", params = "ticker")
    public ModelAndView createTicker(@RequestParam(value = "page", required = false) Long page,
                                     @RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            createTicker(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/leaflet/%d.html?keyword=%s", page, keyword));

    }

    private void createTicker(Long[] ids) {
        List<Long> idList = new LinkedList<>();
        for (Long id : ids) {
            idList.add(id);
        }
        Collections.sort(idList);
        for (Long id : idList) {
            Leaflet leaflet = leafletService.findById(id);
            Ticker ticker = new Ticker();
            ticker.setTitle(leaflet.getTitle());
            ticker.setUrl(createLeafletUrl(leaflet));
            tickerService.save(ticker);
        }
    }

    private String createLeafletUrl(Leaflet leaflet) {
        return String.format("/download/leaflet/document/%d/%s", leaflet.getId(), leaflet.getFilename());
    }


    @RequestMapping(value = "leaflet/{id}/thumb/clear.html")
    public ModelAndView removePhoto(@PathVariable("id") Long id,
                                    @RequestParam(value = "page", required = false) Long page,
                                    @RequestParam(value = "keyword", required = false) String keyword) {
        leafletService.removeThumb(id);
        return new ModelAndView(String.format("redirect:/admin/leaflet/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "popup/leaflet/thumb/upload/{id}.html", method = RequestMethod.GET)
    public ModelAndView uploadPicture(@PathVariable("id") Long id,
                                      @ModelAttribute("form") RepositoryPhotoForm form) {
        ModelAndView mav = new ModelAndView("admin/leaflet/thumb_form");
        mav.addObject("leaflet", leafletService.findById(id));
        return mav;
    }

    @RequestMapping(value = "popup/leaflet/thumb/upload/{id}.html", method = RequestMethod.POST)
    public ModelAndView uploadPicture(@PathVariable("id") Long id,
                                      @ModelAttribute("form") RepositoryPhotoForm form, Errors errors) throws IOException {

        Leaflet leaflet = leafletService.findById(id);
        if (form.getFile() != null && !form.getFile().isEmpty()) {
            leaflet.setThumbFilename(form.getFile().getOriginalFilename());
            leafletService.saveThumb(leaflet, form.getFile().getInputStream());

            ModelAndView mav = new ModelAndView("admin/leaflet/thumb_form_success");
            mav.addObject("leaflet", leaflet);
            return mav;
        }

        ModelAndView mav = new ModelAndView("admin/leaflet/thumb_form");
        mav.addObject("leaflet", leaflet);
        return mav;
    }

}
