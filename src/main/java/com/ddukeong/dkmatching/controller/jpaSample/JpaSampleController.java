package com.ddukeong.dkmatching.controller.jpaSample;

import com.ddukeong.dkmatching.service.jpaSample.JpaSampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/jpa")
public class JpaSampleController {
    private final JpaSampleService service;

    public JpaSampleController(JpaSampleService service) {
        this.service = service;
    }

    @RequestMapping("/sample/home")
    public String getHome(Model model, @RequestParam(required = false, defaultValue = "") String type) {
        if (StringUtils.hasText(type)) {
            model.addAttribute("menuName", type);
            switch (type) {
                case "person":
                    model.addAttribute("entities", service.getPersons());
                    break;
                case "order":
                    model.addAttribute("entities", service.getOrders());
                    break;
                case "food":
                    model.addAttribute("entities", service.getFoods());
                    break;
            }
        }
        return "jpaSample/home.html";
    }

    @RequestMapping("/sample/create/{type}")
    public String getCreatePage(Model model, @PathVariable String type) {
        model.addAttribute("menuName", type);
        if ("order".equals(type)) {
            model.addAttribute("persons", service.getPersons());
            model.addAttribute("foods", service.getFoods());
        }
        return "jpaSample/create.html";
    }

}
