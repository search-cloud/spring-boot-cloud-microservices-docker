package org.asion.webflux.mvc;

import org.asion.webflux.Webflux;
import org.asion.webflux.WebfluxManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Sample controller.
 * @author Asion.
 * @since 16/5/29.
 */
@Controller
@RequestMapping("/sample/")
public class WebDemoController {

    private final WebfluxManager webfluxManager;

    @Autowired
    public WebDemoController(WebfluxManager webfluxManager) {this.webfluxManager = webfluxManager;}

    @GetMapping("list")
    public ModelAndView list() {
        Iterable<Webflux> samples = webfluxManager.findAll();
        return new ModelAndView("sample/list", "samples", samples);
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Webflux sample = webfluxManager.findOne(id);
        return new ModelAndView("sample/view", "sample", sample);
    }

    @GetMapping(params = "form")
    public String createForm(@ModelAttribute Webflux sample) {
        return "sample/form";
    }

    @PostMapping(value = "create")
    public ModelAndView create(@Valid Webflux sample, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("sample/form", "formErrors", result.getAllErrors());
        }
        sample = webfluxManager.save(sample);
        redirect.addFlashAttribute("globalSample", "Successfully save a new sample");
        return new ModelAndView("redirect:/sample/{sample.id}", "sample.id", sample.getId());
    }

    @GetMapping("foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @DeleteMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        webfluxManager.delete(id);
        Iterable<Webflux> samples = webfluxManager.findAll();
        return new ModelAndView("sample/list", "samples", samples);
    }

    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id) {
        Webflux sample = webfluxManager.findOne(id);
        return new ModelAndView("sample/form", "sample", sample);
    }

}
