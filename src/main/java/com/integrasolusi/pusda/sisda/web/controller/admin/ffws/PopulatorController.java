package com.integrasolusi.pusda.sisda.web.controller.admin.ffws;

/**
 * Programmer : pancara
 * Date       : 6/14/13
 * Time       : 11:53 AM
 */

import com.integrasolusi.pusda.sisda.tool.ffws.PopulatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/ffws/populator")
public class PopulatorController {

    @Autowired
    private PopulatorManager populatorManager;

    @RequestMapping("manager.html")
    public ModelAndView mav() {
        return new ModelAndView("admin/ffws/populator/manager");
    }

    @RequestMapping("status.html")
    @ResponseBody
    public Map<String, Object> getStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("running", populatorManager.isRunning());
        return map;
    }

    @RequestMapping("start.html")
    @ResponseBody
    public Map<String, Object> start() {
        Map<String, Object> map = new HashMap<>();
        try {
            populatorManager.start();
            map.put("result", true);
        } catch (Exception e) {
            map.put("result", false);
        }
        return map;
    }

    @RequestMapping("stop.html")
    @ResponseBody
    public Map<String, Object> stop() {
        Map<String, Object> map = new HashMap<>();
        try {
            populatorManager.stop();
            map.put("result", true);
        } catch (Exception e) {
            map.put("result", false);
        }
        return map;
    }
}
